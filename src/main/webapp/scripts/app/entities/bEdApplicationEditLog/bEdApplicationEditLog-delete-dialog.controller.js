'use strict';

angular.module('stepApp')
	.controller('BEdApplicationEditLogDeleteController',
	['$scope', '$modalInstance', 'entity', 'BEdApplicationEditLog',
	function($scope, $modalInstance, entity, BEdApplicationEditLog) {

        $scope.bEdApplicationEditLog = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            BEdApplicationEditLog.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
