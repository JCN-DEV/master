'use strict';

angular.module('stepApp')
	.controller('MpoCommitteeHistoryDeleteController',
	 ['$scope', '$modalInstance', 'entity', 'MpoCommitteeHistory',
	 function($scope, $modalInstance, entity, MpoCommitteeHistory) {

        $scope.mpoCommitteeHistory = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            MpoCommitteeHistory.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
