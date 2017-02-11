'use strict';

angular.module('stepApp')
	.controller('HrDepartmentSetupDeleteController',
	['$scope', '$rootScope', '$modalInstance', 'entity', 'HrDepartmentSetup',
	function($scope, $rootScope, $modalInstance, entity, HrDepartmentSetup) {

        $scope.hrDepartmentSetup = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            HrDepartmentSetup.delete({id: id},
                function () {
                    $modalInstance.close(true);
                    $rootScope.setErrorMessage('stepApp.hrDepartmentSetup.deleted');
                });
        };

    }]);
