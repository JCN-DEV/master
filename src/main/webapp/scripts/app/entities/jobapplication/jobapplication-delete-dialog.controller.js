'use strict';

angular.module('stepApp')
	.controller('JobapplicationDeleteController',
	 ['$scope', '$modalInstance', 'entity', 'Jobapplication',
	 function($scope, $modalInstance, entity, Jobapplication) {

        $scope.jobapplication = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Jobapplication.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
