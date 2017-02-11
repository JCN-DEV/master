'use strict';

angular.module('stepApp').controller('ProfessorApplicationStatusLogDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'ProfessorApplicationStatusLog', 'PrincipleApplication',
        function($scope, $stateParams, $modalInstance, entity, ProfessorApplicationStatusLog, PrincipleApplication) {

        $scope.professorApplicationStatusLog = entity;
        $scope.principleapplications = PrincipleApplication.query();
        $scope.load = function(id) {
            ProfessorApplicationStatusLog.get({id : id}, function(result) {
                $scope.professorApplicationStatusLog = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:professorApplicationStatusLogUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.professorApplicationStatusLog.id != null) {
                ProfessorApplicationStatusLog.update($scope.professorApplicationStatusLog, onSaveSuccess, onSaveError);
            } else {
                ProfessorApplicationStatusLog.save($scope.professorApplicationStatusLog, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
