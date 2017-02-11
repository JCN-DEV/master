'use strict';

angular.module('stepApp')
	.controller('CatDeleteController',
	['$scope', '$rootScope', '$modalInstance', 'entity', 'Cat',
	 function($scope, $rootScope, $modalInstance, entity, Cat) {

        $scope.cat = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Cat.delete({id: id},
                function () {
                    $modalInstance.close(true);
                    $rootScope.setErrorMessage('stepApp.cat.deleted');
                });

        };

    }]);
