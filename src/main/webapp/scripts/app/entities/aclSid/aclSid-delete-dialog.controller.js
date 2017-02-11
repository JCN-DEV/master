'use strict';

angular.module('stepApp')
	.controller('AclSidDeleteController',
	['$scope', '$modalInstance', 'entity', 'AclSid',
	function($scope, $modalInstance, entity, AclSid) {

        $scope.aclSid = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            AclSid.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
