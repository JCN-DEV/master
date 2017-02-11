'use strict';

angular.module('stepApp')
	.controller('DlContUpldDeleteController',
	['$scope', '$modalInstance', 'entity', 'DlContUpld',
	 function($scope, $modalInstance, entity, DlContUpld) {

        $scope.dlContUpld = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            DlContUpld.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
