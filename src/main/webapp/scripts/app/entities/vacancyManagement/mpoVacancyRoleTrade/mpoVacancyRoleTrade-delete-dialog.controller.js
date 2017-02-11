'use strict';

angular.module('stepApp')
	.controller('MpoVacancyRoleTradeDeleteController', function($scope, $modalInstance, entity, MpoVacancyRoleTrade) {

        $scope.mpoVacancyRoleTrade = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            MpoVacancyRoleTrade.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });