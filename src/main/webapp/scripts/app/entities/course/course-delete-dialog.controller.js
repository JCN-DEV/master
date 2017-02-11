'use strict';

angular.module('stepApp')
	.controller('CourseDeleteController',
	['$scope', '$modalInstance', 'entity', 'Course',
	 function($scope, $modalInstance, entity, Course) {

        $scope.course = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Course.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
