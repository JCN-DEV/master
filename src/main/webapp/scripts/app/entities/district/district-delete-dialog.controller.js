'use strict';

angular.module('stepApp')
	.controller('DistrictDeleteController',
	['$scope', '$modalInstance', 'entity', 'District',
	 function($scope, $modalInstance, entity, District) {

        $scope.district = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            District.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
