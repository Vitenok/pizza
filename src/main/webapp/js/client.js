function Controller($scope, $http) {
	
	//ORDER PART
	$scope.totalPrice = 0;
	$scope.address = "";
	$scope.pizzas = [];
	$scope.pizzaIdSeq = 1;
	$scope.currentSelectedPizzaIdInCart = 0;
	$scope.addOrUpdateBtnText="Add To Cart";
	$scope.address="";
	
	$scope.clearSelection = function(){
		angular.forEach($scope.ingredients, function(value, key){
			value.amount=$scope.ingredientAmounts[0];
		});
		$scope.pizzaSize = $scope.pizzaSizes[0];
		$scope.pizzaThicknes = $scope.pizzaThickneses[0];
		$scope.currentSelectedPizzaIdInCart = 0;
		$scope.addOrUpdateBtnText="Add To Cart";
	};
	
	// INGREDIENTS PART
	$scope.pizzaSizes = [{name:"Small pizza", val:"SMALL", multiplier:1},{name:"Medium pizza", val:"MEDIUM", multiplier:1.5},{name:"Large pizza", val:"BIG", multiplier:2}];
	$scope.pizzaSize = $scope.pizzaSizes[0];
	
	$scope.pizzaThickneses = [{name:"Thin pizza", val:"THIN"},{name:"Thick pizza", val:"THICK"}];
	$scope.pizzaThicknes = $scope.pizzaThickneses[0];

	$scope.ingredientGroups = [{name:"MEAT",val:"Meat"},{name:"SEAFOOD",val:"Seafood"},{name:"VEGETABLES",val:"Vegetables"},{name:"SPICES",val:"Spices"},{name:"CHEESE",val:"Cheese"}];
	$scope.ingredientAmounts = [0,1,2,3];
	
	$('#loading').show();
	$('#content').hide();
	$http.get('rest/client/ingredients/all').success(function(data) {
		$scope.ingredients = data;
		$scope.clearSelection();
	});
	
	
	// TAGS PART
	$http.get('rest/client/tags/all').success(function(data) {
		$scope.tags = data;
		angular.forEach($scope.tags, function(value){
			value.checked=false;
		});
	});
	
	
	// TEMPLATES PART
	$http.get('rest/client/templates/all').success(function(data) {
		$scope.templates = data;
		
		$('#loading').fadeOut('slow');
		$('#content').fadeIn('slow');
	});
	
	
	// TAGS <-> TEMPLATES FILTER
	$scope.filterByTag = function(item) {
				
		//check if we have some checked tags
		var checkedTagsIds = _.filter($scope.tags, function(tag){return tag.checked;});
		checkedTagsIds = _.pluck(checkedTagsIds, 'id');
		
		if (checkedTagsIds.length == 0) {
			return true;
		}
		
		//get template tags
		var templateTagsIds = _.pluck(item.tags, 'id');
		
		return _.intersection(checkedTagsIds, templateTagsIds).length > 0;
    };
    
    
    
    // TEMPLATES <-> INGREDIENTS
    $scope.showPizzaIngredients = function(pizza){
    	$scope.clearSelection();
    	
    	//storing currently selected pizza id (if it's from cart)
    	if (_.isUndefined(pizza.isTemplate)){
    		$scope.currentSelectedPizzaIdInCart = pizza.id;
    		$scope.addOrUpdateBtnText="Update Cart";
    	}
    	
    	$scope.pizzaThicknes = _.findWhere($scope.pizzaThickneses, {val:pizza.thickness});
		$scope.pizzaSize = _.findWhere($scope.pizzaSizes, {val:pizza.size});
    	
    	angular.forEach(pizza.ingredients, function(pizzaIngredient){
    		var matchIngredient = _.findWhere($scope.ingredients, {ingredientId : pizzaIngredient.ingredientId});
    		matchIngredient.amount = _.where(pizza.ingredients, {ingredientId : pizzaIngredient.ingredientId}).length;
    	});
    };
    
    
    //CART
    $scope.addToCart = function() {
    	var pizza = {};
    	pizza.thickness = $scope.pizzaThicknes.val;
    	pizza.size = $scope.pizzaSize.val;
    	
    	//ingredient.amount -> amount*ingredient 
    	pizza.ingredients = _.flatten(_.map($scope.ingredients, function(ingredient){
    		return _(ingredient.amount).times(function(n){ return ingredient;});
    	}));
    	
    	//if pizza is not in cart - create id and add to pizzas
    	if ($scope.currentSelectedPizzaIdInCart == 0){
    		pizza.id = $scope.pizzaIdSeq++;
    		$scope.pizzas.push(pizza);
    	} else {
    	//substitute old one with new
    		var el = _.findWhere($scope.pizzas,{id:$scope.currentSelectedPizzaIdInCart});
    		var i = _.indexOf($scope.pizzas, el);
    		pizza.id = $scope.currentSelectedPizzaIdInCart;
    		$scope.pizzas[i] = pizza;
    	}
    	
    	$scope.clearSelection();
    	updateCartPrice();
    };
    
    $scope.removeFromCart = function(pizza) {
    	$scope.pizzas = _.without($scope.pizzas, pizza);
    	updateCartPrice();
    	$scope.clearSelection();
    };
    
    $scope.getPizzaTitleInCart = function(pizza) {
    	return "Pizza " + pizza.id;
    };
    
    $scope.getPizzaPopoverInCart = function(pizza) {
    	return _.uniq(_.pluck(pizza.ingredients,"name")).join(", ");
    };
    
    function updateCartPrice(){
    	$scope.totalPrice=0;
    	angular.forEach($scope.pizzas, function(value, key){
    		$scope.totalPrice += _.reduce(_.pluck(value.ingredients, 'price'), function(memo, price){return memo+price;})*_.findWhere($scope.pizzaSizes, {val:value.size}).multiplier;
    	});
    }
    
    $scope.order = function() {
    	angular.forEach($scope.pizzas, function(pizza){
    		delete pizza.id;
    		angular.forEach(pizza.ingredients, function(ingredient){
        		delete ingredient.amount;
        	});
    	});
    	$http.post('rest/client/placeOrder',{address:$scope.address, pizzas:$scope.pizzas}).success(function(data) {
    		$("#orderPlacedModal").modal("show");
    	});
    	$scope.clearSelection();
		$scope.pizzas=[];
		$scope.totalPrice = 0;
		$scope.address="";
    };
	
}
