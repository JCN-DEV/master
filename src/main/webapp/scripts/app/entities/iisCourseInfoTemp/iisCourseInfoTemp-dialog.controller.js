'use strict';

angular.module('stepApp').controller('IisCourseInfoTempDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'IisCourseInfoTemp', 'IisCurriculumInfo', 'CmsTrade', 'CmsSubject', 'Institute', 'User',
        function($scope, $stateParams, $modalInstance, entity, IisCourseInfoTemp, IisCurriculumInfo, CmsTrade, CmsSubject, Institute, User) {

        $scope.iisCourseInfoTemp = entity;
        $scope.iiscurriculuminfos = IisCurriculumInfo.query();
        $scope.cmstrades = CmsTrade.query();
        $scope.cmssubjects = CmsSubject.query();
        $scope.institutes = Institute.query();
        $scope.users = User.query();
        $scope.load = function(id) {
            IisCourseInfoTemp.get({id : id}, function(result) {
                $scope.iisCourseInfoTemp = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:iisCourseInfoTempUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.iisCourseInfoTemp.id != null) {
                IisCourseInfoTemp.update($scope.iisCourseInfoTemp, onSaveSuccess, onSaveError);
            } else {
                IisCourseInfoTemp.save($scope.iisCourseInfoTemp, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
