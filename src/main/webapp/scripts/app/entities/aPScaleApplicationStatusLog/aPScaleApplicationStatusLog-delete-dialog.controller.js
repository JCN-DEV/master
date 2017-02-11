'use strict';

angular.module('stepApp')
	.controller('APScaleApplicationStatusLogDeleteController',
	['$scope', '$modalInstance', 'entity', 'APScaleApplicationStatusLog',
	function($scope, $modalInstance, entity, APScaleApplicationStatusLog) {

        $scope.aPScaleApplicationStatusLog = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            APScaleApplicationStatusLog.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
