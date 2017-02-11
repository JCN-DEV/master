'use strict';

angular.module('stepApp')
	.controller('HrEmpTransferInfoDeleteController',
	['$scope', '$rootScope', '$modalInstance', 'entity', 'HrEmpTransferInfo',
	function($scope, $rootScope, $modalInstance, entity, HrEmpTransferInfo) {

        $scope.hrEmpTransferInfo = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            HrEmpTransferInfo.delete({id: id},
                function () {
                    $modalInstance.close(true);
                    $rootScope.setErrorMessage('stepApp.hrEmpTransferInfo.deleted');
                });
        };

    }]);
