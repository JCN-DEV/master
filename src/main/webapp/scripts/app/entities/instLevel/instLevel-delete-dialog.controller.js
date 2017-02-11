'use strict';

angular.module('stepApp')
	.controller('InstLevelDeleteController',
    ['$scope', '$modalInstance', 'entity', 'InstLevel',
    function($scope, $modalInstance, entity, InstLevel) {

        $scope.instLevel = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            InstLevel.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
