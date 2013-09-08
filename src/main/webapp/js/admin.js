angular.module("PizzaApp", ["ui.bootstrap"]);

function Controller($scope, $http, $dialog) {
	
	$('#loading').show();
	$('#content').hide();
	$http.get("/rest/admin/admin/all").success(function(data) {
		$scope.admins = data;
		
		$('#loading').fadeOut('slow');
		$('#content').fadeIn('slow');
	});
	
	$scope.adminCommonOpts = {
	    backdrop: true,
	    keyboard: false,
	    backdropClick: false,
	    dialogFade: false,
	    backdropFade: false
	};
	
	//Admins management
	$scope.openAddAdminDialog = function() {
		$scope.adminCommonOpts.templateUrl = "../admin/modals/addOrUpdateAdminModal.html";
		$scope.adminCommonOpts.controller = "AddAdminDialogController";
		$dialog.dialog($scope.adminCommonOpts).open().then(function(result){
			if(result){
				$scope.admins.push(result);
			}
		});
	};
	
	$scope.openUpdateAdminDialog = function(adminToUpdate) {
		$scope.adminCommonOpts.templateUrl = "../admin/modals/addOrUpdateAdminModal.html";
		$scope.adminCommonOpts.controller = "UpdateAdminDialogController";
		$scope.adminCommonOpts.admin = adminToUpdate;
		
		$dialog.dialog($scope.adminCommonOpts).open().then(function(result){
			if(result){
				var el = _.findWhere($scope.admins,{id:result.id});
	    		var i = _.indexOf($scope.admins, el);
	    		$scope.admins[i] = result;
			}
		});
	};
	
	$scope.openDeleteAdminDialog = function(admin){
		var title = "Deleting admin ";
		var msg = "Are you sure you want to delete admin '" + admin.name + "' ?";
		var btns = [{result:"cancel", label: "Cancel"}, {result:"ok", label: "OK", cssClass: "btn-primary"}];
		
		$dialog.messageBox(title, msg, btns).open().then(function(result){
			if (result == "ok") {
				$http.get("/rest/admin/admin/delete/"+admin.id).success(function(data) {
					if (data){
						$scope.admins = _.without($scope.admins, admin);
					}
				});
			}
		});
	};
	
}


// dialogs are injected in the specified controller
function AddAdminDialogController($scope, $http, dialog) {
	$scope.admin = {name:"", password:"", confirmedPassword:""};
	$scope.dialogName = "Add Admin"; 
	
	$scope.doAdmin = function() {
		delete $scope.admin.confirmedPassword;
		
		$http({method: "POST", url: "/rest/admin/admin/add", data: $scope.admin}).
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

function UpdateAdminDialogController($scope, $http, dialog) {
	$scope.admin = dialog.options.admin;
	$scope.admin.confirmedPassword = $scope.admin.password;
	$scope.dialogName = "Update Admin"; 
	
	$scope.doAdmin = function() {
		delete $scope.admin.confirmedPassword;
		
		$http({method: "POST", url: "/rest/admin/admin/update", data: $scope.admin}).
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

