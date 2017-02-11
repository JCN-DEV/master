'use strict';

angular.module('stepApp')
	.controller('HrEmpGovtDuesInfoDeleteController',
	['$scope', '$modalInstance', 'entity', 'HrEmpGovtDuesInfo',
	function($scope, $modalInstance, entity, HrEmpGovtDuesInfo) {

        $scope.hrEmpGovtDuesInfo = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            HrEmpGovtDuesInfo.delete({id: id},
                function () {
                    $modalInstance.close(true);
                    $rootScope.setErrorMessage('stepApp.hrEmpGovtDuesInfo.deleted');
                });
        };

    }]);
