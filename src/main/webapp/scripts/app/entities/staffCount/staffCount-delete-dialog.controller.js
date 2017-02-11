'use strict';

angular.module('stepApp')
	.controller('StaffCountDeleteController',
    ['$scope', '$modalInstance', 'entity', 'StaffCount',
    function($scope, $modalInstance, entity, StaffCount) {

        $scope.staffCount = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            StaffCount.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
