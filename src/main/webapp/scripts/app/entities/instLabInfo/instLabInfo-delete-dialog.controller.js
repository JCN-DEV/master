'use strict';

angular.module('stepApp')
	.controller('InstLabInfoDeleteController',
    ['$scope', '$modalInstance', 'entity', 'InstLabInfo',
    function($scope, $modalInstance, entity, InstLabInfo) {

        $scope.instLabInfo = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            InstLabInfo.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
