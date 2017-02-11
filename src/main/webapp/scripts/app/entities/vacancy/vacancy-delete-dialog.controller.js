'use strict';

angular.module('stepApp')
	.controller('VacancyDeleteController',
    ['$scope', '$modalInstance', 'entity', 'Vacancy',
    function($scope, $modalInstance, entity, Vacancy) {

        $scope.vacancy = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Vacancy.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
