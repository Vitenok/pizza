angular.module("PizzaApp", ["ui.bootstrap"]);

function Controller($scope, $http, $dialog) {
	
	$('#loading').show();
	$('#content').hide();
	$http.get('/rest/admin/ingredients/all').success(function(data) {
		$scope.ingredients = data;
	});
	
	$http.get('/rest/admin/tags/all').success(function(data) {
		$scope.tags = data;
		angular.forEach($scope.tags, function(value){
			value.checked=false;
		});
	});
	
	$http.get("/rest/admin/templates/all").success(function(data) {
		$scope.templates = data;
		angular.forEach($scope.templates, function(template){
			$scope.squezeTemplateIngredients(template);
		});
		
		$('#loading').fadeOut('slow');
		$('#content').fadeIn('slow');
	});
	
	$scope.squezeTemplateIngredients = function(template){
		var ingredients = template.ingredients;
		angular.forEach(template.ingredients, function(templateIngredient){
			templateIngredient.amount = _.where(ingredients, {ingredientId : templateIngredient.ingredientId}).length;
		});
		template.ingredients = _.uniq(template.ingredients,function(ingredient){
		    return ingredient.ingredientId;
		});
	};
	
	// Ingredients part for modal dialog
	$scope.pizzaSizes = [{name:"Small pizza", val:"SMALL", multiplier:1},{name:"Medium pizza", val:"MEDIUM", multiplier:1.5},{name:"Large pizza", val:"BIG", multiplier:2}];
	$scope.pizzaSize = $scope.pizzaSizes[0];
	
	$scope.pizzaThickneses = [{name:"Thin pizza", val:"THIN"},{name:"Thick pizza", val:"THICK"}];
	$scope.pizzaThicknes = $scope.pizzaThickneses[0];

	$scope.ingredientGroups = [{name:"MEAT",val:"Meat"},{name:"SEAFOOD",val:"Seafood"},{name:"VEGETABLES",val:"Vegetables"},{name:"SPICES",val:"Spices"},{name:"CHEESE",val:"Cheese"}];
	$scope.ingredientAmounts = [0,1,2,3];
	$scope.ingredientAmount = $scope.ingredientAmounts[0];
	
	
	
    //Templates management
	$scope.templateCommonOpts = {
		    backdrop: true,
		    keyboard: false,
		    backdropClick: false,
		    dialogFade: false,
		    backdropFade: false
	};
	
	$scope.calculateTemplatePrice = function(template){
		var price = 0;
		angular.forEach(template.ingredients, function(ingredient){
			price += ingredient.price;
		});
		var multiplier = _.findWhere($scope.pizzaSizes, {val:template.size}).multiplier;
		return price * multiplier;
	};		
	
	$scope.openAddTemplateDialog = function(){
		$scope.templateCommonOpts.templateUrl = "../admin/modals/addOrUpdateTemplateModal.html";
		$scope.templateCommonOpts.controller = "AddTemplateDialogController";
		
		$scope.templateCommonOpts.pizzaSizes = $scope.pizzaSizes;
		$scope.templateCommonOpts.pizzaThickneses = $scope.pizzaThickneses;
		$scope.templateCommonOpts.ingredientAmounts = $scope.ingredientAmounts;
		$scope.templateCommonOpts.ingredientGroups = $scope.ingredientGroups;
		
		$scope.templateCommonOpts.ingredients = $scope.ingredients; 
		$scope.templateCommonOpts.tags = $scope.tags;
		
		$dialog.dialog($scope.templateCommonOpts).open().then(function(result){
			if(result){
				$scope.squezeTemplateIngredients(result);
				$scope.templates.push(result);
			}
		});
	};
	
	$scope.openUpdateTemplateDialog = function(template){
		$scope.templateCommonOpts.templateUrl = "../admin/modals/addOrUpdateTemplateModal.html";
		$scope.templateCommonOpts.controller = "UpdateTemplateDialogController"; 
		$scope.templateCommonOpts.pizzaTemplate = template;
		
		$scope.templateCommonOpts.pizzaSizes = $scope.pizzaSizes;
		$scope.templateCommonOpts.pizzaThickneses = $scope.pizzaThickneses;
		$scope.templateCommonOpts.ingredientAmounts = $scope.ingredientAmounts;
		$scope.templateCommonOpts.ingredientGroups = $scope.ingredientGroups;
		
		$scope.templateCommonOpts.ingredients = $scope.ingredients; 
		$scope.templateCommonOpts.tags = $scope.tags;
		
		$dialog.dialog($scope.templateCommonOpts).open().then(function(result){
			if(result){
				$scope.squezeTemplateIngredients(result);
				var el = _.findWhere($scope.templates,{pizzaId:result.pizzaId});
	    		var i = _.indexOf($scope.templates, el);
	    		$scope.templates[i] = result;
			}
		});
	};
	
	$scope.openDeleteTemplateDialog = function(template){
		var title = "Deleting template";
		var msg = "Are you sure you want to delete template '" + template.name + "' ?";
		var btns = [{result:"cancel", label: "Cancel"}, {result:"ok", label: "OK", cssClass: "btn-primary"}];
		
		$dialog.messageBox(title, msg, btns).open().then(function(result){
			if (result == "ok") {
				$http.get("/rest/admin/templates/delete/" + template.pizzaId).success(function(data) {
					if (data){
						$scope.templates = _.without($scope.templates, template);
					}
				}).error(function(){
					alert("This template can not be deleted!");
				});
			}
		});
	};
}


//dialogs are injected in the specified controller
function AddTemplateDialogController($scope, $http, dialog){
	
	$scope.clearSelection = function(){
		angular.forEach($scope.ingredients, function(ingredient){
			ingredient.amount=$scope.ingredientAmounts[0];
		});
		angular.forEach($scope.tags, function(tag){
			tag.checked=false;
		});
		$scope.pizzaSize = $scope.pizzaSizes[0];
		$scope.pizzaThicknes = $scope.pizzaThickneses[0];
	};
	
	$scope.template = {thickness:"", size:"", name:"", ingredients:[], tags:[]};
	$scope.dialogName = "Add Template";
	
	$scope.pizzaSizes = dialog.options.pizzaSizes;
	$scope.pizzaThickneses = dialog.options.pizzaThickneses;
	$scope.ingredientGroups = dialog.options.ingredientGroups;
	$scope.ingredientAmounts = dialog.options.ingredientAmounts;
	
	$scope.pizzaSize = $scope.pizzaSizes[0];
	$scope.pizzaThicknes = $scope.pizzaThickneses[0];
		
	$scope.ingredients = dialog.options.ingredients;
	$scope.tags = dialog.options.tags;
	
	$scope.totalPrice = 0;
	$scope.calculatePrice = function(){
		$scope.totalPrice = 0;
    	angular.forEach($scope.ingredients, function(ingredient){
    		$scope.totalPrice += ingredient.price * ingredient.amount;
    	});
    	$scope.totalPrice = $scope.totalPrice * $scope.pizzaSize.multiplier;
	};
	
	$scope.clearSelection();
	
	$scope.isDisabled = false;
	
	$scope.doTemplate = function(){
		
		var template = {};
		template.name = $scope.template.name;
		template.size = $scope.pizzaSize.val; 
		template.thickness = $scope.pizzaThicknes.val;
		
		template.tags = [];
		var checkedTags = _.filter($scope.tags, function(tag){return tag.checked;});
		angular.forEach(checkedTags, function(tag){
			template.tags.push(_.clone(tag));
		});
		
		template.ingredients = [];
		angular.forEach($scope.ingredients, function(ingredient){
			if (ingredient.amount > 0){
				template.ingredients.push(_.clone(ingredient));
			}
		});
		template.ingredients = _.flatten(_.map(template.ingredients, function(ingredient){
    		return _(ingredient.amount).times(function(n){ return ingredient;});
    	}));
		
		template.isTemplate = true;
		template.order=null;
		
		angular.forEach(template.ingredients, function(ingredient){
    		delete ingredient.amount;
    	});
		angular.forEach(template.tags, function(tag){
    		delete tag.checked;
    	});
		
		$scope.isDisabled = true;
		
		$http({method: "POST", url: "/rest/admin/templates/add", data: template}).
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

function UpdateTemplateDialogController($scope, $http, dialog){
	
	$scope.clearSelection = function(){
		angular.forEach($scope.ingredients, function(ingredient){
			ingredient.amount=$scope.ingredientAmounts[0];
		});
		angular.forEach($scope.tags, function(tag){
			tag.checked=false;
		});
		$scope.pizzaSize = $scope.pizzaSizes[0];
		$scope.pizzaThicknes = $scope.pizzaThickneses[0];
	};
	
	$scope.calculatePrice = function(){
		$scope.totalPrice = 0;
    	angular.forEach($scope.ingredients, function(ingredient){
    		$scope.totalPrice += ingredient.price * ingredient.amount;
    	});
    	$scope.totalPrice = $scope.totalPrice * $scope.pizzaSize.multiplier;
	};
	
	$scope.template = dialog.options.pizzaTemplate;
	$scope.dialogName = "Update Template";
	
	$scope.pizzaSizes = dialog.options.pizzaSizes;
	$scope.pizzaThickneses = dialog.options.pizzaThickneses;
	$scope.ingredientGroups = dialog.options.ingredientGroups;
	$scope.ingredientAmounts = dialog.options.ingredientAmounts;
	$scope.ingredients = dialog.options.ingredients;
	$scope.tags = dialog.options.tags;
	
	$scope.clearSelection();
	
	$scope.isDisabled = false;
	
	//select size
	$scope.pizzaSize = _.findWhere($scope.pizzaSizes, {val:$scope.template.size});
	//select thickness
	$scope.pizzaThicknes = _.findWhere($scope.pizzaThickneses, {val:$scope.template.thickness});
	//select tags
	angular.forEach($scope.template.tags, function(templateTag){
		_.findWhere($scope.tags, {id:templateTag.id}).checked = true;
	});
	//select ingredients
	angular.forEach($scope.template.ingredients, function(templateIngredient){
		var matchIngredient = _.findWhere($scope.ingredients, {ingredientId : templateIngredient.ingredientId});
		matchIngredient.amount = templateIngredient.amount;
	});
	
	$scope.calculatePrice();
	
	$scope.doTemplate = function() {
		
		var template = {};
		template.pizzaId = $scope.template.pizzaId;
		template.name = $scope.template.name;
		template.size = $scope.pizzaSize.val; 
		template.thickness = $scope.pizzaThicknes.val;
		
		template.tags = [];
		var checkedTags = _.filter($scope.tags, function(tag){return tag.checked;});
		angular.forEach(checkedTags, function(tag){
			template.tags.push(_.clone(tag));
		});
		
		template.ingredients = [];
		angular.forEach($scope.ingredients, function(ingredient){
			if (ingredient.amount > 0){
				template.ingredients.push(_.clone(ingredient));
			}
		});
		template.ingredients = _.flatten(_.map(template.ingredients, function(ingredient){
    		return _(ingredient.amount).times(function(n){ return ingredient;});
    	}));
		
		template.isTemplate = true;
		template.order = null;
		
		angular.forEach(template.ingredients, function(ingredient){
    		delete ingredient.amount;
    	});
		angular.forEach(template.tags, function(tag){
    		delete tag.checked;
    	});
		
		$scope.isDisabled = true;
		
		$http({method: "POST", url: "/rest/admin/templates/update", data: template}).
		  success(function(data) {
			  dialog.close(data);
		  }).
		  error(function() {
			  dialog.close();
		  });
	};
	
	$scope.close = function(){
		dialog.close();
	};
	
}
