'use strict';

angular.module('stepApp')
	.controller('ReferenceDeleteController',
    ['$scope','$modalInstance','entity','Reference',
    function($scope, $modalInstance, entity, Reference) {

        $scope.reference = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Reference.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
