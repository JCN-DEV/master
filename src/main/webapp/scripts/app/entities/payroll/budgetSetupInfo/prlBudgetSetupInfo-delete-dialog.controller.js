'use strict';

angular.module('stepApp')
	.controller('PrlBudgetSetupInfoDeleteController', function($scope, $modalInstance, entity, PrlBudgetSetupInfo) {

        $scope.prlBudgetSetupInfo = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            PrlBudgetSetupInfo.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });
