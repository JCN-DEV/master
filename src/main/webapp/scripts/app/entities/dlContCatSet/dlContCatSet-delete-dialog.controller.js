'use strict';

angular.module('stepApp')
	.controller('DlContCatSetDeleteController',
	['$scope', '$modalInstance', 'entity', 'DlContCatSet',
	 function($scope, $modalInstance, entity, DlContCatSet) {

        $scope.dlContCatSet = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            DlContCatSet.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
