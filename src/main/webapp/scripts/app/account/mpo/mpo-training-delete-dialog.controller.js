'use strict';

angular.module('stepApp')
	.controller('MPOTrainingDeleteController',
	['$scope', '$modalInstance', 'entity', 'Training',
	function($scope, $modalInstance, entity, Training) {

        $scope.training = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (trainingId) {
            Training.delete({id: trainingId},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
