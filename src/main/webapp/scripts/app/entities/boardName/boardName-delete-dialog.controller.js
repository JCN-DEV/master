'use strict';

angular.module('stepApp')
	.controller('BoardNameDeleteController',
	['$scope', '$modalInstance', 'entity', 'BoardName',
	function($scope, $modalInstance, entity, BoardName) {

        $scope.boardName = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            BoardName.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
