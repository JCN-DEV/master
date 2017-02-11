'use strict';

angular.module('stepApp').controller('MPOTrainingDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Training', 'District', 'Employee', 'User',
        function($scope, $stateParams, $modalInstance, entity, Training, District, Employee, User) {

        $scope.training = entity;

            $scope.employee = Employee.get({id : $stateParams.id});
        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:trainingUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.training.id != null) {
                Training.update($scope.training, onSaveSuccess, onSaveError);
            } else {
                $scope.training.employee = $scope.employee;
                $scope.training.manager = $scope.employee.manager;
                $scope.training.location = "Dhaka";
                Training.save($scope.training, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
