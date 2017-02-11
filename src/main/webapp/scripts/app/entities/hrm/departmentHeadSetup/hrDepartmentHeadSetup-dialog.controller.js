'use strict';

angular.module('stepApp').controller('HrDepartmentHeadSetupDialogController',
    ['$scope', '$rootScope', '$stateParams', '$state', 'entity', 'HrDepartmentHeadSetup','Principal','User','DateUtils',
        function($scope, $rootScope, $stateParams, $state, entity, HrDepartmentHeadSetup, Principal, User, DateUtils) {

        $scope.hrDepartmentHeadSetup = entity;
        $scope.load = function(id) {
            HrDepartmentHeadSetup.get({id : id}, function(result) {
                $scope.hrDepartmentHeadSetup = result;
            });
        };

        $scope.getLoggedInUser = function ()
        {
            Principal.identity().then(function (account)
            {
                User.get({login: account.login}, function (result)
                {
                    $scope.loggedInUser = result;
                    console.log("loggedInUser: "+JSON.stringify($scope.loggedInUser));
                });
            });
        };
        $scope.getLoggedInUser();

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:hrDepartmentHeadSetupUpdate', result);
            $scope.isSaving = false;
            $state.go('hrDepartmentHeadSetup');
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function ()
        {
            $scope.isSaving = true;
            $scope.hrDepartmentHeadSetup.updateBy = $scope.loggedInUser.id;
            $scope.hrDepartmentHeadSetup.updateDate = DateUtils.convertLocaleDateToServer(new Date());
            if ($scope.hrDepartmentHeadSetup.id != null)
            {
                HrDepartmentHeadSetup.update($scope.hrDepartmentHeadSetup, onSaveSuccess, onSaveError);
                $rootScope.setWarningMessage('stepApp.hrDepartmentHeadSetup.updated');
            } else
            {
                $scope.hrDepartmentHeadSetup.createBy = $scope.loggedInUser.id;
                $scope.hrDepartmentHeadSetup.createDate = DateUtils.convertLocaleDateToServer(new Date());
                HrDepartmentHeadSetup.save($scope.hrDepartmentHeadSetup, onSaveSuccess, onSaveError);
                $rootScope.setSuccessMessage('stepApp.hrDepartmentHeadSetup.created');
            }
        };

        $scope.clear = function() {

        };
}]);
