'use strict';

angular.module('stepApp')
	.controller('DlContSCatSetDeleteController',
	['$scope', '$modalInstance', 'entity', 'DlContSCatSet',
	function($scope, $modalInstance, entity, DlContSCatSet) {

        $scope.dlContSCatSet = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            DlContSCatSet.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
