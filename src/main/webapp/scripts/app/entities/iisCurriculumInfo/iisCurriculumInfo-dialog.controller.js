'use strict';

angular.module('stepApp').controller('IisCurriculumInfoDialogeeeeeeController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'IisCurriculumInfo', 'CmsCurriculum',
        function($scope, $stateParams, $modalInstance, entity, IisCurriculumInfo, CmsCurriculum) {

        $scope.iisCurriculumInfo = entity;
        $scope.cmscurriculums = CmsCurriculum.query();
        $scope.load = function(id) {
            IisCurriculumInfo.get({id : id}, function(result) {
                $scope.iisCurriculumInfo = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:iisCurriculumInfoUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.iisCurriculumInfo.id != null) {
                IisCurriculumInfo.update($scope.iisCurriculumInfo, onSaveSuccess, onSaveError);
            } else {
                IisCurriculumInfo.save($scope.iisCurriculumInfo, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
