'use strict';

angular.module('stepApp')
	.controller('APScaleApplicationEditLogDeleteController',
	['$scope', '$modalInstance', 'entity', 'APScaleApplicationEditLog',
	function($scope, $modalInstance, entity, APScaleApplicationEditLog) {

        $scope.aPScaleApplicationEditLog = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            APScaleApplicationEditLog.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
