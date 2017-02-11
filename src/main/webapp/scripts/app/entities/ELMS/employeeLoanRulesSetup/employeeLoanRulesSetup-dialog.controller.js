'use strict';

angular.module('stepApp').controller('EmployeeLoanRulesSetupDialogController',
    ['$scope','$state' ,'$rootScope', '$stateParams', 'entity', 'EmployeeLoanRulesSetup', 'EmployeeLoanTypeSetup','FindEmployeeLoanTypeSetupByStatus',
        function($scope, $state, $rootScope, $stateParams, entity, EmployeeLoanRulesSetup, EmployeeLoanTypeSetup,FindEmployeeLoanTypeSetupByStatus) {

        $scope.employeeLoanRulesSetup = entity;
        $scope.employeeLoanRulesSetup.status = true;
        $scope.employeeloantypesetups = FindEmployeeLoanTypeSetupByStatus.query();
        $scope.load = function(id) {
            EmployeeLoanRulesSetup.get({id : id}, function(result) {
                $scope.employeeLoanRulesSetup = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('stepApp:employeeLoanRulesSetupUpdate', result);
            $scope.isSaving = false;
            $state.go('employeeLoanInfo.employeeLoanRulesSetup',{},{reload:true});
        };

        $scope.save = function () {
            if ($scope.employeeLoanRulesSetup.id != null) {
                EmployeeLoanRulesSetup.update($scope.employeeLoanRulesSetup, onSaveFinished);
                $rootScope.setWarningMessage('stepApp.employeeLoanRulesSetup.updated');
            } else {
                EmployeeLoanRulesSetup.save($scope.employeeLoanRulesSetup, onSaveFinished);
                $rootScope.setSuccessMessage('stepApp.employeeLoanRulesSetup.created');
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
