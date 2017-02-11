'use strict';

angular.module('stepApp')
	.controller('PrlHouseRentAllowInfoDeleteController', function($scope, $rootScope, $modalInstance, entity, PrlHouseRentAllowInfo) {

        $scope.prlHouseRentAllowInfo = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            PrlHouseRentAllowInfo.delete({id: id},
                function () {
                    $modalInstance.close(true);
                    $rootScope.setErrorMessage('stepApp.prlHouseRentAllowInfo.deleted');
                });
        };

    });
