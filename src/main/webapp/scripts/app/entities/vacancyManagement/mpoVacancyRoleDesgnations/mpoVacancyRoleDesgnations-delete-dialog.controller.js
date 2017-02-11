'use strict';

angular.module('stepApp')
	.controller('MpoVacancyRoleDesgnationsDeleteController', function($scope, $modalInstance, entity, MpoVacancyRoleDesgnations) {

        $scope.mpoVacancyRoleDesgnations = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            MpoVacancyRoleDesgnations.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });