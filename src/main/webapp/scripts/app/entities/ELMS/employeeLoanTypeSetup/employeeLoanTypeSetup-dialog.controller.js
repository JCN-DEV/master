'use strict';

angular.module('stepApp').controller('EmployeeLoanTypeSetupDialogController',
    ['$scope', '$state', '$rootScope', '$stateParams','entity', 'EmployeeLoanTypeSetup',
        function($scope,$state, $rootScope, $stateParams, entity, EmployeeLoanTypeSetup) {

        $scope.employeeLoanTypeSetup = entity;
        $scope.employeeLoanTypeSetup.status = true;
        $scope.load = function(id) {
            EmployeeLoanTypeSetup.get({id : id}, function(result) {
                $scope.employeeLoanTypeSetup = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:employeeLoanTypeSetupUpdate', result);
            $scope.isSaving = false;
            $state.go('employeeLoanInfo.employeeLoanTypeSetup',{},{reload:true});
        };

        var onSaveError = function (result) {
                $scope.isSaving = false;
        };

        $scope.save = function () {
            if ($scope.employeeLoanTypeSetup.id != null) {
                EmployeeLoanTypeSetup.update($scope.employeeLoanTypeSetup,  onSaveSuccess, onSaveError);
                $rootScope.setWarningMessage('stepApp.employeeLoanTypeSetup.updated');
            } else {
                EmployeeLoanTypeSetup.save($scope.employeeLoanTypeSetup,  onSaveSuccess, onSaveError);
                $rootScope.setSuccessMessage('stepApp.employeeLoanTypeSetup.created');
            }
        };

        $scope.clear = function() {

        };
}]);
