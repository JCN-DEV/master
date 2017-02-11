'use strict';

angular.module('stepApp')
	.controller('InstEmplExperienceDeleteController',
	['$scope','$modalInstance','entity','InstEmplExperience',
	 function($scope, $modalInstance, entity, InstEmplExperience) {

        $scope.instEmplExperience = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            InstEmplExperience.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
