'use strict';

angular.module('stepApp').controller('IisCurriculumInfoTempDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'IisCurriculumInfoTemp', 'CmsCurriculum', 'Institute', 'User',
        function($scope, $stateParams, $modalInstance, entity, IisCurriculumInfoTemp, CmsCurriculum, Institute, User) {

        $scope.iisCurriculumInfoTemp = entity;
        $scope.cmscurriculums = CmsCurriculum.query();
        $scope.institutes = Institute.query();
        $scope.users = User.query();
        $scope.load = function(id) {
            IisCurriculumInfoTemp.get({id : id}, function(result) {
                $scope.iisCurriculumInfoTemp = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:iisCurriculumInfoTempUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.iisCurriculumInfoTemp.id != null) {
                IisCurriculumInfoTemp.update($scope.iisCurriculumInfoTemp, onSaveSuccess, onSaveError);
            } else {
                IisCurriculumInfoTemp.save($scope.iisCurriculumInfoTemp, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
