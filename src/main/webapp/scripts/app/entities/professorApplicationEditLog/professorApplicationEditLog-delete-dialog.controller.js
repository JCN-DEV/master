'use strict';

angular.module('stepApp')
	.controller('ProfessorApplicationEditLogDeleteController',
    ['$scope', '$modalInstance', 'entity', 'ProfessorApplicationEditLog',
    function($scope, $modalInstance, entity, ProfessorApplicationEditLog) {

        $scope.professorApplicationEditLog = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            ProfessorApplicationEditLog.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
