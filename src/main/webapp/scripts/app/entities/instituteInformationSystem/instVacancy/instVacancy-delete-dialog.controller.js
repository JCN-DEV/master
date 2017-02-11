'use strict';

angular.module('stepApp')
	.controller('InstVacancyDeleteController', function($scope, $rootScope, $modalInstance, entity, InstVacancy) {

        $scope.instVacancy = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            InstVacancy.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
            $rootScope.setErrorMessage('stepApp.InstVacancy.deleted');
        };

    });
