'use strict';

angular.module('stepApp')
	.controller('OrganizationTypeDeleteController',
    ['$scope','$rootScope','$modalInstance','entity','OrganizationType',
    function($scope, $rootScope, $modalInstance, entity, OrganizationType) {

        $scope.organizationType = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            OrganizationType.delete({id: id},
                function () {
                    $modalInstance.close(true);
                    $rootScope.setErrorMessage('Organization Type has been successfully deleted');
                });
        };

    }]);
