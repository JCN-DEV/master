'use strict';

angular.module('stepApp')
	.controller('TimeScaleApplicationEditLogDeleteController',
    ['$scope', '$modalInstance', 'entity', 'TimeScaleApplicationEditLog',
    function($scope, $modalInstance, entity, TimeScaleApplicationEditLog) {

        $scope.timeScaleApplicationEditLog = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            TimeScaleApplicationEditLog.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
