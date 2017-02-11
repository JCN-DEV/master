'use strict';

angular.module('stepApp')
	.controller('InstituteLabInfoDeleteController',
    ['$scope', '$modalInstance', 'entity', 'InstituteLabInfo',
    function($scope, $modalInstance, entity, InstituteLabInfo) {

        $scope.instituteLabInfo = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            InstituteLabInfo.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
