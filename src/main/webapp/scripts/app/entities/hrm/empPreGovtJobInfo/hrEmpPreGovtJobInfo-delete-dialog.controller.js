'use strict';

angular.module('stepApp')
	.controller('HrEmpPreGovtJobInfoDeleteController',
	['$scope', '$rootScope', '$modalInstance', 'entity', 'HrEmpPreGovtJobInfo',
	function($scope, $rootScope, $modalInstance, entity, HrEmpPreGovtJobInfo) {

        $scope.hrEmpPreGovtJobInfo = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            HrEmpPreGovtJobInfo.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
            $rootScope.setErrorMessage('stepApp.HrEmpPreGovtJobInfo.deleted');
        };

    }]);
