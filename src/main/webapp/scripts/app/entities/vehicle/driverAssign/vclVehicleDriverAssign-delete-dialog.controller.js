'use strict';

angular.module('stepApp')

	.controller('VclVehicleDriverAssignDeleteController',
    ['$scope', '$modalInstance', 'entity', 'VclVehicleDriverAssign',
    function($scope, $modalInstance, entity, VclVehicleDriverAssign) {

        $scope.vclVehicleDriverAssign = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            VclVehicleDriverAssign.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
