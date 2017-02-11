'use strict';

angular.module('stepApp')
	.controller('InstComiteFormationDeleteController',
	['$scope','$modalInstance','entity','InstComiteFormation',
	 function($scope, $modalInstance, entity, InstComiteFormation) {

        $scope.instComiteFormation = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            InstComiteFormation.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
