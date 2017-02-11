'use strict';

angular.module('stepApp')
	.controller('InstLandDeleteController',
    ['$scope', '$modalInstance', 'entity', 'InstLand',
    function($scope, $modalInstance, entity, InstLand) {

        $scope.instLand = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            InstLand.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
