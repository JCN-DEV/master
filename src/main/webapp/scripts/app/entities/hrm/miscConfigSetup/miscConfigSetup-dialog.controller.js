'use strict';

angular.module('stepApp').controller('MiscConfigSetupDialogController',
    ['$scope', '$state','$stateParams', '$rootScope', 'entity', 'MiscConfigSetup','MiscConfigSetupByName','Principal','User','DateUtils',
        function($scope, $state, $stateParams, $rootScope, entity, MiscConfigSetup,MiscConfigSetupByName,Principal,User,DateUtils) {

        $scope.miscConfigSetup = entity;
        $scope.load = function(id) {
            MiscConfigSetup.get({id : id}, function(result) {
                $scope.miscConfigSetup = result;
            });
        };

        $scope.getLoggedInUser = function ()
        {
            Principal.identity().then(function (account)
            {
                User.get({login: account.login}, function (result)
                {
                    $scope.loggedInUser = result;
                });
            });
        };
        $scope.getLoggedInUser();

        var onSaveFinished = function (result) {
            $scope.$emit('stepApp:miscConfigSetupUpdate', result);
            $scope.isSaving = false;
            $state.go('miscConfigSetup');
        };


        $scope.propertyNameAlreadyExist = false;
        $scope.checkPropertyByName = function()
        {
            console.log("NameCheck=> property: "+$scope.miscConfigSetup.propertyName);
            if($scope.miscConfigSetup.propertyName != null && $scope.miscConfigSetup.propertyName.length > 0)
            {
                $scope.editForm.propertyName.$pending = true;
                MiscConfigSetupByName.get({ name: $scope.miscConfigSetup.propertyName}, function(result)
                {
                    console.log(JSON.stringify(result));
                    $scope.editForm.propertyName.$pending = false;
                    if(result!=null)
                    {
                        $scope.propertyNameAlreadyExist = true;
                        $scope.isSaving = false;
                    }
                    else
                    {
                        $scope.propertyNameAlreadyExist = false;
                        $scope.isSaving = true;
                    }
                },function(response)
                {
                    console.log("data connection failed");
                    $scope.editForm.propertyName.$pending = false;
                });
            }

        };

        $scope.save = function ()
        {
            $scope.miscConfigSetup.updateBy = $scope.loggedInUser.id;
            $scope.miscConfigSetup.updateDate = DateUtils.convertLocaleDateToServer(new Date());

            if ($scope.miscConfigSetup.id != null)
            {
                MiscConfigSetup.update($scope.miscConfigSetup, onSaveFinished);
                $rootScope.setWarningMessage('stepApp.miscConfigSetup.updated');
            }
            else
            {
                $scope.miscConfigSetup.createBy = $scope.loggedInUser.id;
                $scope.miscConfigSetup.createDate = DateUtils.convertLocaleDateToServer(new Date());
                MiscConfigSetup.save($scope.miscConfigSetup, onSaveFinished);
                $rootScope.setSuccessMessage('stepApp.miscConfigSetup.created');
            }
        };

        $scope.clear = function() {

        };
}]);
