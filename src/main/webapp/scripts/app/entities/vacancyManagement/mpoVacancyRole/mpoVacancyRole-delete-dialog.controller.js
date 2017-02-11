'use strict';

angular.module('stepApp')
	.controller('MpoVacancyRoleDeleteController', function($scope, $modalInstance, entity, MpoVacancyRole) {

        $scope.mpoVacancyRole = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            MpoVacancyRole.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });