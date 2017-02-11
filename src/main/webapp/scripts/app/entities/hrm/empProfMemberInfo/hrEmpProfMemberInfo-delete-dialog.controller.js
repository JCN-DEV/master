'use strict';

angular.module('stepApp')
	.controller('HrEmpProfMemberInfoDeleteController',
	['$scope', '$rootScope', '$modalInstance', 'entity', 'HrEmpProfMemberInfo',
	function($scope, $rootScope, $modalInstance, entity, HrEmpProfMemberInfo) {

        $scope.hrEmpProfMemberInfo = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            HrEmpProfMemberInfo.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
            $rootScope.setErrorMessage('stepApp.HrEmpProfMemberInfo.deleted');
        };

    }]);
