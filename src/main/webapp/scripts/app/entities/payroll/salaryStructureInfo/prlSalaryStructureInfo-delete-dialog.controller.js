'use strict';

angular.module('stepApp')
	.controller('PrlSalaryStructureInfoDeleteController', function($scope, $modalInstance, entity, PrlSalaryStructureInfo) {

        $scope.prlSalaryStructureInfo = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            PrlSalaryStructureInfo.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });
