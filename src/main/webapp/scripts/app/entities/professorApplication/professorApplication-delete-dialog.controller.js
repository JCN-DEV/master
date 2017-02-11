'use strict';

angular.module('stepApp')
	.controller('ProfessorApplicationDeleteController',
    ['$scope', '$modalInstance', 'entity', 'ProfessorApplication',
    function($scope, $modalInstance, entity, ProfessorApplication) {

        $scope.professorApplication = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            ProfessorApplication.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
