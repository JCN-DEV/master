'use strict';

angular.module('stepApp')
	.controller('TimeScaleApplicationStatusLogDeleteController',
    ['$scope', '$modalInstance', 'entity', 'TimeScaleApplicationStatusLog',
    function($scope, $modalInstance, entity, TimeScaleApplicationStatusLog) {

        $scope.timeScaleApplicationStatusLog = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            TimeScaleApplicationStatusLog.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
