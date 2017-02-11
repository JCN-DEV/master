'use strict';

angular.module('stepApp')
	.controller('InstMemShipDeleteController',
    ['$scope', '$rootScope', '$modalInstance', 'entity', 'InstMemShip',
    function($scope, $rootScope, $modalInstance, entity, InstMemShip) {

        $scope.instMemShip = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            InstMemShip.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
            $rootScope.setErrorMessage('stepApp.InstMemShip.deleted');
        };

    }]);
