'use strict';

angular.module('stepApp')
	.controller('InstEmpAddressDeleteController',
	['$scope', '$rootScope' , '$modalInstance', 'entity', 'InstEmpAddress',
	 function($scope, $rootScope , $modalInstance, entity, InstEmpAddress) {

        $scope.instEmpAddress = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            InstEmpAddress.delete({id: id},
                function () {
                    $modalInstance.close(true);
                    $rootScope.setSuccessMessage('stepApp.instEmpAddress.deleted');
                });
        };

    }]);
