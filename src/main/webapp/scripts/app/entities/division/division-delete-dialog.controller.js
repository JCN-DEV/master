'use strict';

angular.module('stepApp')
	.controller('DivisionDeleteController',
	['$scope', '$rootScope', '$modalInstance', 'entity', 'Division',
	function($scope, $rootScope, $modalInstance, entity, Division) {

        $scope.division = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Division.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
            $rootScope.setErrorMessage('stepApp.division.deleted');
        };

    }]);
