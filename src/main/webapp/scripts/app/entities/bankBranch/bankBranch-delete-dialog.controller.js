'use strict';

angular.module('stepApp')
	.controller('BankBranchDeleteController', function($scope, $modalInstance, entity, BankBranch) {

        $scope.bankBranch = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            BankBranch.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });