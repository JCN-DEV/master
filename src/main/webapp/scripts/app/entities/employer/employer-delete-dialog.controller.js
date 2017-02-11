/*
'use strict';

angular.module('stepApp')
	.controller('EmployerDeleteController', function($scope, $modalInstance, entity, Employer) {

        $scope.employer = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Employer.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });
*/
