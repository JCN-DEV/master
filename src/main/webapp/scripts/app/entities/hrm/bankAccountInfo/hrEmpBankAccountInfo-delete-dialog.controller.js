'use strict';

angular.module('stepApp')
	.controller('HrEmpBankAccountInfoDeleteController',
	['$scope', '$rootScope', '$modalInstance', 'entity', 'HrEmpBankAccountInfo',
	function($scope, $rootScope, $modalInstance, entity, HrEmpBankAccountInfo) {

        $scope.hrEmpBankAccountInfo = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            HrEmpBankAccountInfo.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
            $rootScope.setErrorMessage('stepApp.HrEmpBankAccountInfo.deleted');
        };

    }]);
