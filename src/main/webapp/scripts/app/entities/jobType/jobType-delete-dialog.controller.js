'use strict';

angular.module('stepApp')
	.controller('JobTypeDeleteController',
	['$scope', '$rootScope', '$modalInstance', 'entity', 'JobType',
	function($scope, $rootScope, $modalInstance, entity, JobType) {

        $scope.jobType = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            JobType.delete({id: id},
                function () {
                    $modalInstance.close(true);
                    $rootScope.setErrorMessage('stepApp.jobType.deleted');
                });
        };

    }]);
