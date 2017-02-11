'use strict';

angular.module('stepApp')
	.controller('AclObjectIdentityDeleteController',
	['$scope', '$modalInstance', 'entity', 'AclObjectIdentity',
	function($scope, $modalInstance, entity, AclObjectIdentity) {

        $scope.aclObjectIdentity = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            AclObjectIdentity.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
