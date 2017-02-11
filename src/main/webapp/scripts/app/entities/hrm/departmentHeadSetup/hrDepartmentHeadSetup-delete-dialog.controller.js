'use strict';

angular.module('stepApp')
	.controller('HrDepartmentHeadSetupDeleteController', function($scope, $rootScope, $modalInstance, entity, HrDepartmentHeadSetup) {

        $scope.hrDepartmentHeadSetup = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            HrDepartmentHeadSetup.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
            $rootScope.setErrorMessage('stepApp.HrDepartmentHeadSetup.deleted');
        };

    });
