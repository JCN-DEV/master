'use strict';

angular.module('stepApp')
	.controller('BankAssignDeleteController', function($scope, $modalInstance, entity, BankAssign) {

        $scope.bankAssign = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            BankAssign.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });