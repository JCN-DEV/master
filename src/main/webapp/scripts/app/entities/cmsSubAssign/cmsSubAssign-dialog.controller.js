'use strict';

angular.module('stepApp').controller('CmsSubAssignDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'CmsSubAssign', 'CmsCurriculum', 'CmsTrade', 'CmsSemester', 'CmsSyllabus',
        function($scope, $stateParams, $modalInstance, entity, CmsSubAssign, CmsCurriculum, CmsTrade, CmsSemester, CmsSyllabus) {

        $scope.cmsSubAssign = entity;
        $scope.cmscurriculums = CmsCurriculum.query();
        $scope.cmstrades = CmsTrade.query();
        $scope.cmssemesters = CmsSemester.query();
        $scope.cmssyllabuss = CmsSyllabus.query();
        $scope.load = function(id) {
            CmsSubAssign.get({id : id}, function(result) {
                $scope.cmsSubAssign = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:cmsSubAssignUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.cmsSubAssign.id != null) {
                CmsSubAssign.update($scope.cmsSubAssign, onSaveSuccess, onSaveError);
            } else {
                CmsSubAssign.save($scope.cmsSubAssign, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
