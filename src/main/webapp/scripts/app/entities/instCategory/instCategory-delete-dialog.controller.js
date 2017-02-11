'use strict';

angular.module('stepApp')
	.controller('InstCategoryDeleteController',
	['$scope', '$rootScope','$modalInstance','entity','InstCategory',
	 function($scope, $rootScope, $modalInstance, entity, InstCategory) {

        $scope.instCategory = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            InstCategory.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
            $rootScope.setErrorMessage('stepApp.InstCategory.deleted');
        };

    }]);
