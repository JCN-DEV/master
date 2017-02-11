'use strict';

angular.module('stepApp')
	.controller('InstitutePlayGroundInfoDeleteController',
    ['$scope', '$modalInstance', 'entity', 'InstitutePlayGroundInfo',
    function($scope, $modalInstance, entity, InstitutePlayGroundInfo) {

        $scope.institutePlayGroundInfo = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            InstitutePlayGroundInfo.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
