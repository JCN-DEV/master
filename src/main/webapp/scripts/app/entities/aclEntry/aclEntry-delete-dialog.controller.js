'use strict';

angular.module('stepApp')
	.controller('AclEntryDeleteController',
	['$scope', '$modalInstance', 'entity', 'AclEntry',
	 function($scope, $modalInstance, entity, AclEntry) {

        $scope.aclEntry = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            AclEntry.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
