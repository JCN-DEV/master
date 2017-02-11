'use strict';

angular.module('stepApp')
	.controller('InstBuildingDeleteController',
	['$scope','$modalInstance','entity','InstBuilding',
	 function($scope, $modalInstance, entity, InstBuilding) {

        $scope.instBuilding = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            InstBuilding.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
