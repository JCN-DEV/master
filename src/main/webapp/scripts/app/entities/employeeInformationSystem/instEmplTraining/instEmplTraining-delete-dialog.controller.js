'use strict';

angular.module('stepApp')
	.controller('InstEmplTrainingDeleteController',
	['$scope', '$modalInstance', 'entity', 'InstEmplTraining',
	function($scope, $modalInstance, entity, InstEmplTraining) {

        $scope.instEmplTraining = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            InstEmplTraining.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });$rootScope.setErrorMessage('stepApp.instEmplTraining.deleted');

        };

    }]);
