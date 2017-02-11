'use strict';

angular.module('stepApp').controller('CourseSubDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'CourseSub', 'CourseTech',
        function($scope, $stateParams, $modalInstance, entity, CourseSub, CourseTech) {

        $scope.courseSub = entity;
        $scope.coursetechs = CourseTech.query();
        $scope.load = function(id) {
            CourseSub.get({id : id}, function(result) {
                $scope.courseSub = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:courseSubUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.courseSub.id != null) {
                CourseSub.update($scope.courseSub, onSaveSuccess, onSaveError);
            } else {
                CourseSub.save($scope.courseSub, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
