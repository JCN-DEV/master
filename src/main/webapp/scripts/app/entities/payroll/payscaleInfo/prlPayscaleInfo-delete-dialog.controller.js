'use strict';

angular.module('stepApp')
	.controller('PrlPayscaleInfoDeleteController', function($scope, $modalInstance, entity, PrlPayscaleInfo) {

        $scope.prlPayscaleInfo = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            PrlPayscaleInfo.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });
