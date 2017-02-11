'use strict';

angular.module('stepApp')
	.controller('InstShopInfoDeleteController',
    ['$scope', '$modalInstance', 'entity', 'InstShopInfo',
    function($scope, $modalInstance, entity, InstShopInfo) {

        $scope.instShopInfo = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            InstShopInfo.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
