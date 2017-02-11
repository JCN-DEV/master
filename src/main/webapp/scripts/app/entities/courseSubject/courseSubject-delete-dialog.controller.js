'use strict';

angular.module('stepApp')
	.controller('CourseSubjectDeleteController',
	['$scope', '$modalInstance', 'entity', 'CourseSubject',
	function($scope, $modalInstance, entity, CourseSubject) {

        $scope.courseSubject = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            CourseSubject.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
