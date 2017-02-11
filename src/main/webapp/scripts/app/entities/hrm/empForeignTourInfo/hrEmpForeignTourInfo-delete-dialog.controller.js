'use strict';

angular.module('stepApp')
	.controller('HrEmpForeignTourInfoDeleteController',
	['$scope', '$rootScope', '$modalInstance', 'entity', 'HrEmpForeignTourInfo',
	function($scope, $rootScope, $modalInstance, entity, HrEmpForeignTourInfo) {

        $scope.hrEmpForeignTourInfo = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            HrEmpForeignTourInfo.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
            $rootScope.setErrorMessage('stepApp.HrEmpForeignTourInfo.deleted');
        };

    }]);
