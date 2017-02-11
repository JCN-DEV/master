'use strict';

angular.module('stepApp')
	.controller('DlContTypeSetDeleteController',
	['$scope', '$modalInstance', 'entity', 'DlContTypeSet',
	 function($scope, $modalInstance, entity, DlContTypeSet) {

        $scope.dlContTypeSet = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            DlContTypeSet.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
