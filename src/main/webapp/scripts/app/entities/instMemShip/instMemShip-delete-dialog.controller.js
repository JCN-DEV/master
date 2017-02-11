'use strict';

angular.module('stepApp')
	.controller('InstMemShipDeleteController',
    ['$scope', '$modalInstance', 'entity', 'InstMemShip',
    function($scope, $modalInstance, entity, InstMemShip) {

        $scope.instMemShip = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            InstMemShip.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
