angular.module("PizzaApp", ["ui.bootstrap"]);

function Controller($scope, $http, $dialog) {
	
	$('#loading').show();
	$('#content').hide();
	$http.get('/rest/admin/ingredients/all').success(function(data) {
		$scope.ingredients = data;
		
		$('#loading').fadeOut('slow');
		$('#content').fadeIn('slow');
	});
	
	$scope.ingredientGroups = [{name:"MEAT",val:"Meat"},{name:"SEAFOOD",val:"Seafood"},{name:"VEGETABLES",val:"Vegetables"},{name:"SPICES",val:"Spices"},{name:"CHEESE",val:"Cheese"}];
	$scope.ingredientGroup = $scope.ingredientGroups[0];
	
	$scope.ingredientCommonOpts = {
	    backdrop: true,
	    keyboard: false,
	    backdropClick: false,
	    dialogFade: false,
	    backdropFade: false
	};
	
	//Ingredients management
	$scope.openDeleteIngredientDialog = function(ingredient){
		var title = "Deleting Ingredient";
		var msg = "Are you sure you want to delete ingredient '" + ingredient.name + "' ?";
		var btns = [{result:"cancel", label: "Cancel"}, {result:"ok", label: "OK", cssClass: "btn-primary"}];
		
		$dialog.messageBox(title, msg, btns).open().then(function(result){
			if (result == "ok") {
				$http.get("/rest/admin/ingredients/delete/"+ingredient.ingredientId).success(function(data) {
					if (data){
						$scope.ingredients = _.without($scope.ingredients, ingredient);
					}
				}).error(function(){
					alert("This ingredient can not be deleted!");
				});
			}
		});
	};
	
	$scope.openAddIngredientDialog = function(ingredient){
		$scope.ingredientCommonOpts.templateUrl = "../admin/modals/addOrUpdateIngredientModal.html";
		$scope.ingredientCommonOpts.controller = "AddIngredientDialogController";
		$scope.ingredientCommonOpts.ingredientGroups = $scope.ingredientGroups;

		$dialog.dialog($scope.ingredientCommonOpts).open().then(function(result){
			if(result){
				$scope.ingredients.push(result);
			}
		});
	};
	
	$scope.openUpdateIngredientDialog = function(ingredientToUpdate){
		$scope.ingredientCommonOpts.templateUrl = "../admin/modals/addOrUpdateIngredientModal.html";
		$scope.ingredientCommonOpts.controller = "UpdateIngredientDialogController"; 
		$scope.ingredientCommonOpts.ingredient = ingredientToUpdate;
		$scope.ingredientCommonOpts.ingredientGroups = $scope.ingredientGroups;
		
		$dialog.dialog($scope.ingredientCommonOpts).open().then(function(result){
			if(result){
				var el = _.findWhere($scope.ingredients,{ingredientId:result.ingredientId});
	    		var i = _.indexOf($scope.ingredients, el);
	    		$scope.ingredients[i] = result;
			}
		});
	};
}


// dialogs are injected in the specified controller
function AddIngredientDialogController($scope, $http, dialog){
	$scope.ingredient = {name:"", price:"", ingredientType:""};
	$scope.ingredientGroups = dialog.options.ingredientGroups;
	$scope.ingredientGroup = {};
	
	$scope.dialogName = "Add Ingredient";
	
	$scope.doIngredient = function(){
		$scope.ingredient.ingredientType = $scope.ingredientGroup.name;
		
		$http({method: "POST", url: "/rest/admin/ingredients/add", data: $scope.ingredient}).
		  success(function(data) {
			  dialog.close(data);
		  }).
		  error(function() {
			  alert("Error!!!");
			  dialog.close();
		  });
	};
	
	$scope.close = function(){
		dialog.close();
	};
}

function UpdateIngredientDialogController($scope, $http, dialog){
	$scope.ingredient = dialog.options.ingredient;
	$scope.ingredientGroups = dialog.options.ingredientGroups;
	$scope.ingredientGroup = _.findWhere($scope.ingredientGroups, {name:$scope.ingredient.ingredientType});
	
	$scope.dialogName = "Update Ingredient"; 
	
	$scope.doIngredient = function() {
		$scope.ingredient.ingredientType = $scope.ingredientGroup.name; 
		$http({method: "POST", url: "/rest/admin/ingredients/update", data: $scope.ingredient}).
		  success(function(data) {
			  dialog.close(data);
		  }).
		  error(function() {
			  alert("Error!!!");
			  dialog.close();
		  });
	};
	
	$scope.close = function(){
		dialog.close();
	};
}