'use strict';

angular.module('stepApp')
	.controller('JobDeleteController',
	 ['$scope', '$modalInstance', 'entity', 'Job',
	 function($scope, $modalInstance, entity, Job) {

        $scope.job = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Job.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
