'use strict';

angular.module('stepApp')
	.controller('InstituteShopInfoDeleteController',
    ['$scope', '$modalInstance', 'entity', 'InstituteShopInfo',
    function($scope, $modalInstance, entity, InstituteShopInfo) {

        $scope.instituteShopInfo = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            InstituteShopInfo.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
