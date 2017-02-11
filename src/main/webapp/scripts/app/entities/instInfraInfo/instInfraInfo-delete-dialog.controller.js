'use strict';

angular.module('stepApp')
	.controller('InstInfraInfoDeleteController',
    ['$scope','$modalInstance','entity','InstInfraInfo',
    function($scope, $modalInstance, entity, InstInfraInfo) {

        $scope.instInfraInfo = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            InstInfraInfo.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
