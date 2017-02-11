'use strict';

angular.module('stepApp')
	.controller('PrlPayscaleAllowanceInfoDeleteController', function($scope, $modalInstance, entity, PrlPayscaleAllowanceInfo) {

        $scope.prlPayscaleAllowanceInfo = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            PrlPayscaleAllowanceInfo.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });
