'use strict';

angular.module('stepApp')
	.controller('PrlPayscaleBasicInfoDeleteController', function($scope, $uibModalInstance, entity, PrlPayscaleBasicInfo) {

        $scope.prlPayscaleBasicInfo = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            PrlPayscaleBasicInfo.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
