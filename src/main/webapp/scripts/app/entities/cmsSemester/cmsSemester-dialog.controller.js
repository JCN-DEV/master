'use strict';

angular.module('stepApp').controller('CmsSemesterDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'CmsSemester', 'CmsCurriculum',
        function($scope, $stateParams, $modalInstance, entity, CmsSemester, CmsCurriculum) {

        $scope.cmsSemester = entity;
        $scope.cmscurriculums = CmsCurriculum.query();
        $scope.load = function(id) {
            CmsSemester.get({id : id}, function(result) {
                $scope.cmsSemester = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:cmsSemesterUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.cmsSemester.id != null) {
                CmsSemester.update($scope.cmsSemester, onSaveSuccess, onSaveError);
            } else {
                CmsSemester.save($scope.cmsSemester, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
