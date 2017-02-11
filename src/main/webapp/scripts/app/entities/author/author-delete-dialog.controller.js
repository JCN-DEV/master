'use strict';

angular.module('stepApp')
	.controller('AuthorDeleteController',
	['$scope', '$modalInstance', 'entity', 'Author',
	function($scope, $modalInstance, entity, Author) {

        $scope.author = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Author.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
