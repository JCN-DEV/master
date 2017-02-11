'use strict';

angular.module('stepApp')
	.controller('InstPlayGroundInfoDeleteController',
    ['$scope', '$modalInstance', 'entity', 'InstPlayGroundInfo',
    function($scope, $modalInstance, entity, InstPlayGroundInfo) {

        $scope.instPlayGroundInfo = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            InstPlayGroundInfo.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
