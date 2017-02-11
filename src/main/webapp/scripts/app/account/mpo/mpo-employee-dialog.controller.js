'use strict';

angular.module('stepApp').controller('MPOEmployeeDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Principal', 'Employee', 'User', 'Institute',
        function ($scope, $stateParams, $modalInstance, entity, Principal, Employee, User, Institute) {

            $scope.employee = entity;
            $scope.users = User.query();
            $scope.institutes = Institute.query();

            Principal.identity().then(function (account) {
                $scope.account = account;
                $scope.employee.manager = User.get({login: $scope.account.login});
            });

            $scope.employee.institute = Institute.get({id: 'my'});


            $scope.load = function (id) {
                Employee.get({id: id}, function (result) {
                    $scope.employee = result;
                });
            };

            var onSaveSuccess = function (result) {
                $scope.$emit('stepApp:employeeUpdate', result);
                $modalInstance.close(result);
                $scope.isSaving = false;
            };

            var onSaveError = function (result) {
                $scope.isSaving = false;
            };

            $scope.save = function () {
                $scope.isSaving = true;
                if ($scope.employee.id != null) {
                    Employee.update($scope.employee, onSaveSuccess, onSaveError);
                } else {
                    Employee.save($scope.employee, onSaveSuccess, onSaveError);
                }
            };

            $scope.clear = function () {
                $modalInstance.dismiss('cancel');
            };
        }]);
