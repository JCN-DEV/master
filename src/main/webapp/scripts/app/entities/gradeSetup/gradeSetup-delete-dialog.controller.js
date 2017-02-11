'use strict';

angular.module('stepApp')
	.controller('GradeSetupDeleteController',
	['$scope','$modalInstance','entity','GradeSetup',
	function($scope, $modalInstance, entity, GradeSetup) {

        $scope.gradeSetup = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            GradeSetup.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
