'use strict';

angular.module('stepApp').controller('HrWingSetupDialogController',
    ['$rootScope','$scope', '$state', '$stateParams', 'entity', 'HrWingSetup', 'HrEmployeeInfosByDesigLevel','Principal','User','DateUtils','HrWingHeadSetupByWing',
        function($rootScope, $scope, $state, $stateParams, entity, HrWingSetup, HrEmployeeInfosByDesigLevel, Principal,User,DateUtils,HrWingHeadSetupByWing) {

        $scope.hrWingSetup = entity;
        $scope.hremployeeinfos = [];
        $scope.hrWingHeadSetups = [];

        HrEmployeeInfosByDesigLevel.get({desigLevel : 3}, function(result) {
            $scope.hremployeeinfos = result;
        });

        $scope.load = function(id) {
            HrWingSetup.get({id : id}, function(result) {
                $scope.hrWingSetup = result;
            });
        };

        $scope.parentWingId = 0;
        $scope.getWingHeadList = function ()
        {
            if($stateParams.id)
            {
                console.log("Load head info list parent id: "+$stateParams.id);
                $scope.parentWingId = $stateParams.id;
                HrWingHeadSetupByWing.get({wingId : $stateParams.id}, function(result) {
                    console.log("totalResult: "+result.length);
                    $scope.hrWingHeadSetups = result;
                });
            }
            else
            {
                $scope.parentWingId = 0;
            }
            console.log("list parentWingId: "+$scope.parentWingId);
        };


        $scope.loggedInUser =   {};
        $scope.getLoggedInUser = function ()
        {
            Principal.identity().then(function (account)
            {
                User.get({login: account.login}, function (result)
                {
                    $scope.loggedInUser = result;
                     $scope.getWingHeadList();
                });
            });
        };
        $scope.getLoggedInUser();

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:hrWingSetupUpdate', result);
            $scope.isSaving = false;
            $state.go('hrWingSetup');
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            $scope.hrWingSetup.updateBy = $scope.loggedInUser.id;
            $scope.hrWingSetup.updateDate = DateUtils.convertLocaleDateToServer(new Date());
            if ($scope.hrWingSetup.id != null)
            {
                HrWingSetup.update($scope.hrWingSetup, onSaveSuccess, onSaveError);
                $rootScope.setWarningMessage('stepApp.hrWingSetup.updated');
            }
            else
            {
                $scope.hrWingSetup.createBy = $scope.loggedInUser.id;
                $scope.hrWingSetup.createDate = DateUtils.convertLocaleDateToServer(new Date());
                HrWingSetup.save($scope.hrWingSetup, onSaveSuccess, onSaveError);
                $rootScope.setSuccessMessage('stepApp.hrWingSetup.created');
            }
        };

        $scope.clear = function() {

        };
}]);
