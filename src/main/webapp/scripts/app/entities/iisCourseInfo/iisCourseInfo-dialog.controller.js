'use strict';

angular.module('stepApp').controller('IisCourseInfoDialogeeeeeeeeeController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'IisCourseInfo', 'IisCurriculumInfo', 'CmsTrade', 'CmsSubject',
        function($scope, $stateParams, $modalInstance, entity, IisCourseInfo, IisCurriculumInfo, CmsTrade, CmsSubject) {

        $scope.iisCourseInfo = entity;
        $scope.iiscurriculuminfos = IisCurriculumInfo.query();
        $scope.cmstrades = CmsTrade.query();
        $scope.cmssubjects = CmsSubject.query();
        $scope.load = function(id) {
            IisCourseInfo.get({id : id}, function(result) {
                $scope.iisCourseInfo = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:iisCourseInfoUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.iisCourseInfo.id != null) {
                IisCourseInfo.update($scope.iisCourseInfo, onSaveSuccess, onSaveError);
            } else {
                IisCourseInfo.save($scope.iisCourseInfo, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
