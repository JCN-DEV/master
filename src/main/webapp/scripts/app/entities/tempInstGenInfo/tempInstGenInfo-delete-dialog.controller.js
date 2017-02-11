'use strict';

angular.module('stepApp')
	.controller('TempInstGenInfoDeleteController',
    ['$scope', '$modalInstance', 'entity', 'TempInstGenInfo',
    function($scope, $modalInstance, entity, TempInstGenInfo) {

        $scope.tempInstGenInfo = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            TempInstGenInfo.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
