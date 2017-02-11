'use strict';

angular.module('stepApp')
	.controller('PrlEmpPaymentStopInfoDeleteController', function($scope, $modalInstance, entity, PrlEmpPaymentStopInfo) {

        $scope.prlEmpPaymentStopInfo = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            PrlEmpPaymentStopInfo.delete({id: id},
                function () {
                    $modalInstance.close(true);
                    $rootScope.setErrorMessage('stepApp.prlEmpPaymentStopInfo.deleted');
                });
        };

    });
