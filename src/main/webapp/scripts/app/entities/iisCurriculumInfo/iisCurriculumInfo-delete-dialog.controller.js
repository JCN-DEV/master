'use strict';

angular.module('stepApp')
	.controller('IisCurriculumInfoDeleteeeeeeeeeeController',
	['$scope','$modalInstance','entity','IisCurriculumInfo',
	 function($scope, $modalInstance, entity, IisCurriculumInfo) {

        $scope.iisCurriculumInfo = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            IisCurriculumInfo.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
