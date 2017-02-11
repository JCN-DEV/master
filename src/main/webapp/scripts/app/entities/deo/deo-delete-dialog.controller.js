'use strict';

angular.module('stepApp')
	.controller('DeoDeleteController', function($scope, $modalInstance, entity, Deo) {

        $scope.deo = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Deo.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });