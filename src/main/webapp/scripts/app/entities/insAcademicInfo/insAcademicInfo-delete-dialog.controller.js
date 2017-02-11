'use strict';

angular.module('stepApp')
	.controller('InsAcademicInfoDeleteController',
	['$scope','$modalInstance','entity','InsAcademicInfo',
	 function($scope, $modalInstance, entity, InsAcademicInfo) {

        $scope.insAcademicInfo = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            InsAcademicInfo.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
