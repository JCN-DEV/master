'use strict';

angular.module('stepApp')
	.controller('InstEmplDesignationDeleteController',
	['$scope','$modalInstance','entity','InstEmplDesignation',
	 function($scope, $modalInstance, entity, InstEmplDesignation) {

        $scope.instEmplDesignation = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            InstEmplDesignation.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
