'use strict';

angular.module('stepApp')
	.controller('MpoTradeDeleteController', function($scope, $modalInstance, entity, MpoTrade) {

        $scope.mpoTrade = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            MpoTrade.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });