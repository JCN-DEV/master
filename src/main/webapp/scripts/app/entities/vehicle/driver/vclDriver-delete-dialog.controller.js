'use strict';

angular.module('stepApp')
	.controller('VclDriverDeleteController',
    ['$scope', '$modalInstance', 'entity', 'VclDriver',
    function($scope, $modalInstance, entity, VclDriver) {

        $scope.vclDriver = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            VclDriver.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
