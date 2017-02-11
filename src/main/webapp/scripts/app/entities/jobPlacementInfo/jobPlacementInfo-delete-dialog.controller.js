'use strict';

angular.module('stepApp')
	.controller('JobPlacementInfoDeleteController',
    ['$scope', '$modalInstance', 'entity', 'JobPlacementInfo',
    function($scope, $modalInstance, entity, JobPlacementInfo) {

        $scope.jobPlacementInfo = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            JobPlacementInfo.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
