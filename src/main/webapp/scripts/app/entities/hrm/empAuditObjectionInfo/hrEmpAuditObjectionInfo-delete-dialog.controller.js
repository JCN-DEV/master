'use strict';

angular.module('stepApp')
	.controller('HrEmpAuditObjectionInfoDeleteController',
	['$scope', '$rootScope', '$modalInstance', 'entity', 'HrEmpAuditObjectionInfo',
	function($scope, $rootScope, $modalInstance, entity, HrEmpAuditObjectionInfo) {

        $scope.hrEmpAuditObjectionInfo = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            HrEmpAuditObjectionInfo.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
            $rootScope.setErrorMessage('stepApp.HrEmpAuditObjectionInfo.deleted');
        };

    }]);
