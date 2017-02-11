'use strict';

angular.module('stepApp')
	.controller('HrEmpActingDutyInfoDeleteController',
	 ['$scope', '$rootScope', '$modalInstance', 'entity', 'HrEmpActingDutyInfo',
	 function($scope, $rootScope, $modalInstance, entity, HrEmpActingDutyInfo) {

        $scope.hrEmpActingDutyInfo = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            HrEmpActingDutyInfo.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
            $rootScope.setErrorMessage('stepApp.HrEmpActingDutyInfo.deleted');
        };

    }]);
