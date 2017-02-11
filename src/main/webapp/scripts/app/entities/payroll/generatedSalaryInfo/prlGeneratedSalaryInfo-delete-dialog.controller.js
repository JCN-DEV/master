'use strict';

angular.module('stepApp')
	.controller('PrlGeneratedSalaryInfoDeleteController', function($scope, $rootScope, $modalInstance, entity, PrlGeneratedSalaryInfo) {

        $scope.prlGeneratedSalaryInfo = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            PrlGeneratedSalaryInfo.delete({id: id},
                function () {
                    $modalInstance.close(true);
                    $rootScope.setErrorMessage('stepApp.prlGeneratedSalaryInfo.deleted');
                });
        };

    });
