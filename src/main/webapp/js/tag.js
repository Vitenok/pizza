angular.module("PizzaApp", ["ui.bootstrap"]);

function Controller($scope, $http, $dialog) {
	
	$('#loading').show();
	$('#content').hide();
	$http.get("/rest/admin/tags/all").success(function(data) {
		$scope.tags = data;
		
		$('#loading').fadeOut('slow');
		$('#content').fadeIn('slow');
	});
	
	$scope.tagCommonOpts = {
	    backdrop: true,
	    keyboard: false,
	    backdropClick: false,
	    dialogFade: false,
	    backdropFade: false
	};
	
	
    //Tags management
	$scope.openAddTagDialog = function(admin){
		$scope.tagCommonOpts.templateUrl = "../admin/modals/addOrUpdateTagModal.html";
		$scope.tagCommonOpts.controller = "AddTagDialogController";                   
		$dialog.dialog($scope.tagCommonOpts).open().then(function(result){
			if(result){
				$scope.tags.push(result);
			}
		});
	};
	
	$scope.openUpdateTagDialog = function(tagToUpdate){
		$scope.tagCommonOpts.templateUrl = "../admin/modals/addOrUpdateTagModal.html";
		$scope.tagCommonOpts.controller = "UpdateTagDialogController"; 
		$scope.tagCommonOpts.tag = tagToUpdate;
		
		$dialog.dialog($scope.tagCommonOpts).open().then(function(result){
			if(result){
				var el = _.findWhere($scope.tags,{id:result.id});
	    		var i = _.indexOf($scope.tags, el);
	    		$scope.tags[i] = result;
			}
		});
	};
	
	$scope.openDeleteTagDialog = function(tag){
		var title = "Deleting Tag ";
		var msg = "Are you sure you want to delete tag '" + tag.name + "' ?";
		var btns = [{result:"cancel", label: "Cancel"}, {result:"ok", label: "OK", cssClass: "btn-primary"}];
		
		$dialog.messageBox(title, msg, btns).open().then(function(result){
			if (result == "ok") {
				$http.get("/rest/admin/tags/delete/"+tag.id).success(function(data) {
					if (data){
						$scope.tags = _.without($scope.tags, tag);
					}
				}).error(function(){
					alert("This tag can not be deleted!");
				});
			}
		});
	};
	
}


// dialogs are injected in the specified controller
function AddTagDialogController($scope, $http, dialog){
	$scope.tag = {name:""};
	$scope.dialogName = "Add Tag";
	
	$scope.doTag = function(){
		$http({method: "POST", url: "/rest/admin/tags/add", data: $scope.tag}).
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

function UpdateTagDialogController($scope, $http, dialog){
	$scope.tag = dialog.options.tag;
	$scope.dialogName = "Update Tag"; 
	
	$scope.doTag = function() {
		
		$http({method: "POST", url: "/rest/admin/tags/update", data: $scope.tag}).
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
