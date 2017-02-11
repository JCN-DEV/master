'use strict';

angular.module('stepApp')
	.controller('HrEmpAdvIncrementInfoDeleteController',
	['$scope', '$rootScope', '$modalInstance', 'entity', 'HrEmpAdvIncrementInfo',
	function($scope, $rootScope, $modalInstance, entity, HrEmpAdvIncrementInfo) {

        $scope.hrEmpAdvIncrementInfo = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            HrEmpAdvIncrementInfo.delete({id: id},
                function () {
                    $modalInstance.close(true);
                    $rootScope.setErrorMessage('stepApp.hrEmpAdvIncrementInfo.deleted');
                });
        };

    }]);
