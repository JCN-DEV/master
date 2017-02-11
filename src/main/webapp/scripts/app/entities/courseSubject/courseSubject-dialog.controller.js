'use strict';

angular.module('stepApp').controller('CourseSubjectDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'CourseSubject', 'CourseTech',
        function($scope, $stateParams, $modalInstance, entity, CourseSubject, CourseTech) {

        $scope.courseSubject = entity;
        $scope.coursetechs = CourseTech.query();
        $scope.load = function(id) {
            CourseSubject.get({id : id}, function(result) {
                $scope.courseSubject = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:courseSubjectUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.courseSubject.id != null) {
                CourseSubject.update($scope.courseSubject, onSaveSuccess, onSaveError);
            } else {
                CourseSubject.save($scope.courseSubject, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
