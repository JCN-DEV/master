'use strict';

angular.module('stepApp')
	.controller('HrEmpAcrInfoDeleteController',
	['$scope', '$rootScope', '$modalInstance', 'entity', 'HrEmpAcrInfo',
	function($scope, $rootScope, $modalInstance, entity, HrEmpAcrInfo) {

        $scope.hrEmpAcrInfo = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            HrEmpAcrInfo.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
            $rootScope.setErrorMessage('stepApp.HrEmpAcrInfo.deleted');
        };

    }]);
