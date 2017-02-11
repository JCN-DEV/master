'use strict';

angular.module('stepApp')
	.controller('InstFinancialInfoTempDeleteController', function($scope, $modalInstance, entity, InstFinancialInfoTemp) {

        $scope.instFinancialInfoTemp = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            InstFinancialInfoTemp.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });