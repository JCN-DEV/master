'use strict';

angular.module('stepApp')
	.controller('HrEmpAwardInfoDeleteController',
	['$scope', '$rootScope', '$modalInstance', 'entity', 'HrEmpAwardInfo',
	function($scope, $rootScope, $modalInstance, entity, HrEmpAwardInfo) {

        $scope.hrEmpAwardInfo = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            HrEmpAwardInfo.delete({id: id},
                function () {
                    $modalInstance.close(true);
                    $rootScope.setErrorMessage('stepApp.hrEmpAwardInfo.deleted');
                });
        };

    }]);
