'use strict';

angular.module('stepApp')
	.controller('HrEmpWorkAreaDtlInfoDeleteController',
	['$scope','$rootScope', '$modalInstance', 'entity', 'HrEmpWorkAreaDtlInfo',
	function($scope, $rootScope, $modalInstance, entity, HrEmpWorkAreaDtlInfo) {

        $scope.hrEmpWorkAreaDtlInfo = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            HrEmpWorkAreaDtlInfo.delete({id: id},
                function () {
                    $modalInstance.close(true);
                    $rootScope.setErrorMessage('stepApp.hrEmpWorkAreaDtlInfo.deleted');
                });
        };

    }]);
