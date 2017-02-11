'use strict';

angular.module('stepApp')
	.controller('AclClassDeleteController',
	['$scope', '$modalInstance', 'entity', 'AclClass',
	function($scope, $modalInstance, entity, AclClass) {

        $scope.aclClass = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            AclClass.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
