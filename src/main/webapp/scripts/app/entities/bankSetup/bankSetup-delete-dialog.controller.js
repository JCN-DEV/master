'use strict';

angular.module('stepApp')
	.controller('BankSetupDeleteController',
	['$scope', '$modalInstance', 'entity', 'BankSetup',
	function($scope, $modalInstance, entity, BankSetup) {

        $scope.bankSetup = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            BankSetup.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
