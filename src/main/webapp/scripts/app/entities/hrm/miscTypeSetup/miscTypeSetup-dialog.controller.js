'use strict';

angular.module('stepApp').controller('MiscTypeSetupDialogController',
    ['$rootScope','$scope', '$stateParams', '$modalInstance', 'entity', 'MiscTypeSetup','DateUtils','Principal','User','MiscTypeSetupByCatNameOrTitle',
        function($rootScope,  $scope, $stateParams, $modalInstance, entity, MiscTypeSetup,DateUtils,Principal,User,MiscTypeSetupByCatNameOrTitle) {

        $scope.miscTypeSetup = entity;
        $scope.load = function(id) {
            MiscTypeSetup.get({id : id}, function(result) {
                $scope.miscTypeSetup = result;
            });
        };

        $scope.getLoggedInUser = function ()
        {
            Principal.identity().then(function (account)
            {
                User.get({login: account.login}, function (result)
                {
                    $scope.loggedInUser = result;
                    //console.log("loggedInUser: "+JSON.stringify($scope.loggedInUser));
                });
            });
        };
        $scope.getLoggedInUser();

        $scope.typeNameAlreadyExist = false;
        $scope.typeTitleAlreadyExist = false;
        $scope.checkCategoryWiseTypeName = function()
        {
            console.log("NameCheck=> category: "+$scope.miscTypeSetup.typeCategory+", value: "+$scope.miscTypeSetup.typeName);
            if($scope.miscTypeSetup.typeName != null && $scope.miscTypeSetup.typeName.length > 0)
            {
                $scope.editForm.typeName.$pending = true;
                MiscTypeSetupByCatNameOrTitle.get({chktype:'name', cat: $scope.miscTypeSetup.typeCategory, value:$scope.miscTypeSetup.typeName}, function(result)
                {
                    console.log(JSON.stringify(result));
                    $scope.editForm.typeName.$pending = false;
                    if(result.isValid)
                    {
                        $scope.typeNameAlreadyExist = false;
                        if($scope.typeTitleAlreadyExist==false)
                            $scope.isSaving = !result.isValid;
                    }
                    else
                    {
                        $scope.typeNameAlreadyExist = true;
                        $scope.isSaving = !result.isValid;
                    }
                },function(response)
                {
                    console.log("data connection failed");
                    $scope.editForm.typeName.$pending = false;
                });
            }

        };

        $scope.checkCategoryWiseTypeTitle = function()
        {
            console.log("TitleCheck=> category: "+$scope.miscTypeSetup.typeCategory+", value: "+$scope.miscTypeSetup.typeTitle)
            if($scope.miscTypeSetup.typeTitle != null && $scope.miscTypeSetup.typeTitle.length > 0)
            {
                $scope.editForm.typeTitle.$pending = true;
                MiscTypeSetupByCatNameOrTitle.get({chktype:'title', cat: $scope.miscTypeSetup.typeCategory, value:$scope.miscTypeSetup.typeTitle}, function(result)
                {
                    console.log(JSON.stringify(result));
                    $scope.editForm.typeTitle.$pending = false;
                    if(result.isValid)
                    {
                        if($scope.typeNameAlreadyExist==false)
                            $scope.isSaving = !result.isValid;
                        $scope.typeTitleAlreadyExist = false;
                    }
                    else
                    {
                        $scope.typeTitleAlreadyExist = true;
                        $scope.isSaving = !result.isValid;
                    }
                },function(response)
                {
                    console.log("data connection failed");
                    $scope.editForm.typeTitle.$pending = false;
                });
            }
        };

        var onSaveSuccess = function (result)
        {
            $modalInstance.dismiss('cancel');
            $scope.isSaving = false;
            console.log("OnSaveSuccess: emit to miscTypeSetupUpdateByCategory");
            $rootScope.$emit('miscTypeSetupUpdateByCategory', result);
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function ()
        {
            $scope.isSaving = true;
            $scope.miscTypeSetup.updateBy = $scope.loggedInUser.id;
            $scope.miscTypeSetup.updateDate = DateUtils.convertLocaleDateToServer(new Date());
            if ($scope.miscTypeSetup.id != null)
            {
                MiscTypeSetup.update($scope.miscTypeSetup, onSaveSuccess, onSaveError);
                $rootScope.setWarningMessage('stepApp.miscTypeSetup.updated');
            }
            else
            {
                $scope.miscTypeSetup.createBy = $scope.loggedInUser.id;
                $scope.miscTypeSetup.createDate = DateUtils.convertLocaleDateToServer(new Date());
                MiscTypeSetup.save($scope.miscTypeSetup, onSaveSuccess, onSaveError);
                $rootScope.setSuccessMessage('stepApp.miscTypeSetup.created');
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
