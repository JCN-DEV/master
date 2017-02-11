'use strict';

angular.module('stepApp')
	.controller('PersonalPayDeleteController', function($scope, $modalInstance, entity, PersonalPay) {

        $scope.personalPay = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            PersonalPay.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });