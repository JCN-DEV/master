'use strict';

angular.module('stepApp')
	.controller('InstGovBodyAccessDeleteController', function($scope, $modalInstance, entity, InstGovBodyAccess) {

        $scope.instGovBodyAccess = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            InstGovBodyAccess.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });