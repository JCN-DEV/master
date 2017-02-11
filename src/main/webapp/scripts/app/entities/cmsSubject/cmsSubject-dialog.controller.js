'use strict';

angular.module('stepApp').controller('CmsSubjectDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'CmsSubject', 'CmsCurriculum', 'CmsSyllabus',
        function($scope, $stateParams, $modalInstance, entity, CmsSubject, CmsCurriculum, CmsSyllabus) {

        $scope.cmsSubject = entity;
        $scope.cmscurriculums = CmsCurriculum.query();
        $scope.cmssyllabuss = CmsSyllabus.query();
        $scope.load = function(id) {
            CmsSubject.get({id : id}, function(result) {
                $scope.cmsSubject = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:cmsSubjectUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.cmsSubject.id != null) {
                CmsSubject.update($scope.cmsSubject, onSaveSuccess, onSaveError);
            } else {
                CmsSubject.save($scope.cmsSubject, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
