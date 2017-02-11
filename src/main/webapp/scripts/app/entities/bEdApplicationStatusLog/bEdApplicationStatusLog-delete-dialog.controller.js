'use strict';

angular.module('stepApp')
	.controller('BEdApplicationStatusLogDeleteController',
	['$scope', '$modalInstance', 'entity', 'BEdApplicationStatusLog',
	function($scope, $modalInstance, entity, BEdApplicationStatusLog) {

        $scope.bEdApplicationStatusLog = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            BEdApplicationStatusLog.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
