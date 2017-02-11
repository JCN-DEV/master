'use strict';

angular.module('stepApp').controller('EmployeeLoanCheckRegisterDialogController',
    ['$scope', '$stateParams', '$modalInstance', '$q', 'entity', 'EmployeeLoanCheckRegister', 'InstEmployee', 'EmployeeLoanRequisitionForm', 'EmployeeLoanBillRegister',
        function($scope, $stateParams, $modalInstance, $q, entity, EmployeeLoanCheckRegister, InstEmployee, EmployeeLoanRequisitionForm, EmployeeLoanBillRegister) {

        $scope.employeeLoanCheckRegister = entity;
        $scope.instemployees = InstEmployee.query();
        $scope.employeeloanrequisitionforms = EmployeeLoanRequisitionForm.query();
        $scope.employeeloanbillregisters = EmployeeLoanBillRegister.query({filter: 'employeeloancheckregister-is-null'});
        $q.all([$scope.employeeLoanCheckRegister.$promise, $scope.employeeloanbillregisters.$promise]).then(function() {
            if (!$scope.employeeLoanCheckRegister.employeeLoanBillRegister.id) {
                return $q.reject();
            }
            return EmployeeLoanBillRegister.get({id : $scope.employeeLoanCheckRegister.employeeLoanBillRegister.id}).$promise;
        }).then(function(employeeLoanBillRegister) {
            $scope.employeeloanbillregisters.push(employeeLoanBillRegister);
        });
        $scope.load = function(id) {
            EmployeeLoanCheckRegister.get({id : id}, function(result) {
                $scope.employeeLoanCheckRegister = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('stepApp:employeeLoanCheckRegisterUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.employeeLoanCheckRegister.id != null) {
                EmployeeLoanCheckRegister.update($scope.employeeLoanCheckRegister, onSaveFinished);
            } else {
                EmployeeLoanCheckRegister.save($scope.employeeLoanCheckRegister, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
