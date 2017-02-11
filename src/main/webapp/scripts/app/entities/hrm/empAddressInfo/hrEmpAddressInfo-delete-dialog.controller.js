'use strict';

angular.module('stepApp')
	.controller('HrEmpAddressInfoDeleteController',
	 ['$scope', '$rootScope', '$modalInstance', 'entity', 'HrEmpAddressInfo',
	 function($scope, $rootScope, $modalInstance, entity, HrEmpAddressInfo) {

        $scope.hrEmpAddressInfo = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            HrEmpAddressInfo.delete({id: id},
                function () {
                    $modalInstance.close(true);
                    $rootScope.setErrorMessage('stepApp.hrEmpAddressInfo.deleted');
                });
        };

    }]);
