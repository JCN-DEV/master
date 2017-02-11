'use strict';

angular.module('stepApp').controller('CmsSyllabusDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'CmsSyllabus', 'CmsCurriculum',
        function($scope, $stateParams, $modalInstance, entity, CmsSyllabus, CmsCurriculum) {

        $scope.cmsSyllabus = entity;
        $scope.cmscurriculums = CmsCurriculum.query();
        $scope.load = function(id) {
            CmsSyllabus.get({id : id}, function(result) {
                $scope.cmsSyllabus = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:cmsSyllabusUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.cmsSyllabus.id != null) {
                CmsSyllabus.update($scope.cmsSyllabus, onSaveSuccess, onSaveError);
            } else {
                CmsSyllabus.save($scope.cmsSyllabus, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
