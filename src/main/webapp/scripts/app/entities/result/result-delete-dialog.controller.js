'use strict';

angular.module('stepApp')
	.controller('ResultDeleteController',
    ['$scope', '$modalInstance', 'entity', 'Result',
    function($scope, $modalInstance, entity, Result) {

        $scope.result = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Result.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
