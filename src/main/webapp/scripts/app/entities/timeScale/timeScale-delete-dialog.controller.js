'use strict';

angular.module('stepApp')
	.controller('TimeScaleDeleteController',
    ['$scope', '$modalInstance', 'entity', 'TimeScale',
    function($scope, $modalInstance, entity, TimeScale) {

        $scope.timeScale = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            TimeScale.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
