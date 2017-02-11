'use strict';

angular.module('stepApp').controller('CmsCurriculumDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'CmsCurriculum',
        function($scope, $stateParams, $modalInstance, entity, CmsCurriculum) {

        $scope.cmsCurriculum = entity;
        $scope.load = function(id) {
            CmsCurriculum.get({id : id}, function(result) {
                $scope.cmsCurriculum = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:cmsCurriculumUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.cmsCurriculum.id != null) {
                CmsCurriculum.update($scope.cmsCurriculum, onSaveSuccess, onSaveError);
            } else {
                CmsCurriculum.save($scope.cmsCurriculum, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
