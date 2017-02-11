'use strict';

angular.module('stepApp')
	.controller('HrEmpPromotionInfoDeleteController',
	['$scope', '$rootScope', '$modalInstance', 'entity', 'HrEmpPromotionInfo',
	function($scope, $rootScope, $modalInstance, entity, HrEmpPromotionInfo) {

        $scope.hrEmpPromotionInfo = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            HrEmpPromotionInfo.delete({id: id},
                function () {
                    $modalInstance.close(true);
                    $rootScope.setErrorMessage('stepApp.hrEmpPromotionInfo.deleted');
                });
        };

    }]);
