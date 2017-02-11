'use strict';

angular.module('stepApp')
	.controller('PrincipalRequirementDeleteController',
    ['$scope', '$modalInstance', 'entity', 'PrincipalRequirement',
    function($scope, $modalInstance, entity, PrincipalRequirement) {

        $scope.principalRequirement = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            PrincipalRequirement.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
