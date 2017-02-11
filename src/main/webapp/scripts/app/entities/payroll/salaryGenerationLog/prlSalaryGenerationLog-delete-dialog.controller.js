'use strict';

angular.module('stepApp')
	.controller('PrlSalaryGenerationLogDeleteController', function($scope, $rootScope, $modalInstance, entity, PrlSalaryGenerationLog) {

        $scope.prlSalaryGenerationLog = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            PrlSalaryGenerationLog.delete({id: id},
                function () {
                    $modalInstance.close(true);
                    $rootScope.setErrorMessage('stepApp.prlSalaryGenerationLog.deleted');
                });
        };

    });
