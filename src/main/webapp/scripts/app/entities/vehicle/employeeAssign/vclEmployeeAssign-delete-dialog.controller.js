'use strict';

angular.module('stepApp')
	.controller('VclEmployeeAssignDeleteController',
    ['$scope', '$modalInstance', 'entity', 'VclEmployeeAssign',
    function($scope, $modalInstance, entity, VclEmployeeAssign) {

        $scope.vclEmployeeAssign = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            VclEmployeeAssign.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
