'use strict';

angular.module('stepApp').controller('CourseDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Course', 'Institute',
        function($scope, $stateParams, $modalInstance, entity, Course, Institute) {

        $scope.course = entity;
        $scope.institutes = Institute.query({size: 500});
        $scope.load = function(id) {
            Course.get({id : id}, function(result) {
                $scope.course = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:courseUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.course.id != null) {
                Course.update($scope.course, onSaveSuccess, onSaveError);
            } else {
                Course.save($scope.course, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
