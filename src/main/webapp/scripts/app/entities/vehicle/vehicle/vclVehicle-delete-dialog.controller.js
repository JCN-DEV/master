'use strict';

angular.module('stepApp')
	.controller('VclVehicleDeleteController',
    ['$scope', '$modalInstance', 'entity', 'VclVehicle',
    function($scope, $modalInstance, entity, VclVehicle) {

        $scope.vclVehicle = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            VclVehicle.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
