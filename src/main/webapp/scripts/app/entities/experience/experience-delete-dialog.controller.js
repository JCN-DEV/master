'use strict';

angular.module('stepApp')
	.controller('ExperienceDeleteController',
	['$scope', '$modalInstance', 'entity', 'Experience',
	function($scope, $modalInstance, entity, Experience) {

        $scope.experience = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Experience.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
