'use strict';

angular.module('stepApp').controller('DepartmentDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Department',
        function($scope, $stateParams, $modalInstance, entity, Department) {

        $scope.department = entity;
        $scope.load = function(id) {
            Department.get({id : id}, function(result) {
                $scope.department = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:departmentUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.department.id != null) {
                Department.update($scope.department, onSaveSuccess, onSaveError);
            } else {
                Department.save($scope.department, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
