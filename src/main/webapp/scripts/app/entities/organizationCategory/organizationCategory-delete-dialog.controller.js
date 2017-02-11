'use strict';

angular.module('stepApp')
	.controller('OrganizationCategoryDeleteController',

    ['$scope','$rootScope','$modalInstance','entity','OrganizationCategory',
    function($scope, $rootScope, $modalInstance, entity, OrganizationCategory) {

        $scope.organizationCategory = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            OrganizationCategory.delete({id: id},
                function () {
                    $modalInstance.close(true);
                    $rootScope.setErrorMessage('Deleted');
                });
        };

    }]);
