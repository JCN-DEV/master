'use strict';

angular.module('stepApp')
	.controller('HrEmpTransferApplInfoDeleteController',
	 ['$scope','$rootScope', '$modalInstance', 'entity', 'HrEmpTransferApplInfo',
	 function($scope,$rootScope, $modalInstance, entity, HrEmpTransferApplInfo) {

        $scope.hrEmpTransferApplInfo = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            HrEmpTransferApplInfo.delete({id: id},
                function () {
                    $modalInstance.close(true);
                    $rootScope.setErrorMessage('stepApp.hrEmpTransferApplInfo.deleted');
                });
        };

    }]);
