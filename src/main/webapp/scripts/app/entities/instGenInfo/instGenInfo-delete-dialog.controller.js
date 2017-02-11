'use strict';

angular.module('stepApp')
	.controller('InstGenInfoDeleteController',
    ['$scope','$modalInstance','entity','InstGenInfo',
    function($scope, $modalInstance, entity, InstGenInfo) {

        $scope.instGenInfo = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            InstGenInfo.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
