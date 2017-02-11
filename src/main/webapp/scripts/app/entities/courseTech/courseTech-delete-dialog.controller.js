'use strict';

angular.module('stepApp')
	.controller('CourseTechDeleteController',
	['$scope', '$modalInstance', 'entity', 'CourseTech',
	 function($scope, $modalInstance, entity, CourseTech) {

        $scope.courseTech = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            CourseTech.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
