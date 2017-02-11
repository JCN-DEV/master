'use strict';

angular.module('stepApp')
	.controller('InstituteInfoDeleteController',
    ['$scope', '$modalInstance', 'entity', 'InstituteInfo',
    function($scope, $modalInstance, entity, InstituteInfo) {

        $scope.instituteInfo = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            InstituteInfo.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
