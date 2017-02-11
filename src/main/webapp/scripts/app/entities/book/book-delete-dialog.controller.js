'use strict';

angular.module('stepApp')
	.controller('BookDeleteController',
	['$scope', '$modalInstance', 'entity', 'Book',
	function($scope, $modalInstance, entity, Book) {

        $scope.book = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Book.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
