'use strict';

angular.module('stepApp')
	.controller('CourseSubDeleteController',
	['$scope', '$modalInstance', 'entity', 'CourseSub',
	function($scope, $modalInstance, entity, CourseSub) {

        $scope.courseSub = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            CourseSub.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
