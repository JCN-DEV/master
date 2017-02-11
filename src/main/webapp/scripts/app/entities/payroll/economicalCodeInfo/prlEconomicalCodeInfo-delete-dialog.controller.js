'use strict';

angular.module('stepApp')
	.controller('PrlEconomicalCodeInfoDeleteController', function($scope, $modalInstance, entity, PrlEconomicalCodeInfo) {

        $scope.prlEconomicalCodeInfo = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            PrlEconomicalCodeInfo.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });
