'use strict';

angular.module('stepApp')
	.controller('HrEmpOtherServiceInfoDeleteController',
	['$scope', '$rootScope', '$modalInstance', 'entity', 'HrEmpOtherServiceInfo',
	function($scope, $rootScope, $modalInstance, entity, HrEmpOtherServiceInfo) {

        $scope.hrEmpOtherServiceInfo = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            HrEmpOtherServiceInfo.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
            $rootScope.setErrorMessage('stepApp.HrEmpOtherServiceInfo.deleted');
        };

    }]);
