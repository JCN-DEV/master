'use strict';

angular.module('stepApp').controller('VacancyDialogController',
    ['$scope', '$stateParams', '$modalInstance', '$q', 'entity', 'Vacancy', 'Employee', 'User',
        function($scope, $stateParams, $modalInstance, $q, entity, Vacancy, Employee, User) {

        $scope.vacancy = entity;
        $scope.employees = Employee.query({filter: 'vacancy-is-null'});
        $q.all([$scope.vacancy.$promise, $scope.employees.$promise]).then(function() {
            if (!$scope.vacancy.employee.id) {
                return $q.reject();
            }
            return Employee.get({id : $scope.vacancy.employee.id}).$promise;
        }).then(function(employee) {
            $scope.employees.push(employee);
        });
        $scope.users = User.query();
        $scope.load = function(id) {
            Vacancy.get({id : id}, function(result) {
                $scope.vacancy = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:vacancyUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.vacancy.id != null) {
                Vacancy.update($scope.vacancy, onSaveSuccess, onSaveError);
            } else {
                Vacancy.save($scope.vacancy, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
