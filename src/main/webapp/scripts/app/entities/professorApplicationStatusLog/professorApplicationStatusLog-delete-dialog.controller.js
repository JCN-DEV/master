'use strict';

angular.module('stepApp')
	.controller('ProfessorApplicationStatusLogDeleteController',
    ['$scope','$modalInstance','entity','ProfessorApplicationStatusLog',
    function($scope, $modalInstance, entity, ProfessorApplicationStatusLog) {

        $scope.professorApplicationStatusLog = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            ProfessorApplicationStatusLog.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
