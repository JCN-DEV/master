'use strict';

angular.module('stepApp').controller('ProfessorApplicationEditLogDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'ProfessorApplicationEditLog', 'PrincipleApplication',
        function($scope, $stateParams, $modalInstance, entity, ProfessorApplicationEditLog, PrincipleApplication) {

        $scope.professorApplicationEditLog = entity;
        $scope.principleapplications = PrincipleApplication.query();
        $scope.load = function(id) {
            ProfessorApplicationEditLog.get({id : id}, function(result) {
                $scope.professorApplicationEditLog = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:professorApplicationEditLogUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.professorApplicationEditLog.id != null) {
                ProfessorApplicationEditLog.update($scope.professorApplicationEditLog, onSaveSuccess, onSaveError);
            } else {
                ProfessorApplicationEditLog.save($scope.professorApplicationEditLog, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
