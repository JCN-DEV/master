'use strict';

angular.module('stepApp').controller('ProfessorApplicationDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'ProfessorApplication', 'InstEmployee',
        function($scope, $stateParams, $modalInstance, entity, ProfessorApplication, InstEmployee) {

        $scope.professorApplication = entity;
        $scope.instemployees = InstEmployee.query();
        $scope.load = function(id) {
            ProfessorApplication.get({id : id}, function(result) {
                $scope.professorApplication = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:professorApplicationUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.professorApplication.id != null) {
                ProfessorApplication.update($scope.professorApplication, onSaveSuccess, onSaveError);
            } else {
                ProfessorApplication.save($scope.professorApplication, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
