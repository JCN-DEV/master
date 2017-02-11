'use strict';

angular.module('stepApp').controller('InstEmplTrainingDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'InstEmplTraining', 'InstEmployee',
        function($scope, $stateParams, $modalInstance, entity, InstEmplTraining, InstEmployee) {

        $scope.instEmplTraining = entity;
        $scope.instemployees = InstEmployee.query();
        $scope.load = function(id) {
            InstEmplTraining.get({id : id}, function(result) {
                $scope.instEmplTraining = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:instEmplTrainingUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.instEmplTraining.id != null) {
                InstEmplTraining.update($scope.instEmplTraining, onSaveSuccess, onSaveError);
            } else {
                InstEmplTraining.save($scope.instEmplTraining, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
