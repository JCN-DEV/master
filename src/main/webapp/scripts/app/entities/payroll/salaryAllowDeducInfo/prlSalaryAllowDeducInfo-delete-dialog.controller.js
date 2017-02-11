'use strict';

angular.module('stepApp')
	.controller('PrlSalaryAllowDeducInfoDeleteController', function($scope, $modalInstance, entity, PrlSalaryAllowDeducInfo) {

        $scope.prlSalaryAllowDeducInfo = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            PrlSalaryAllowDeducInfo.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });
