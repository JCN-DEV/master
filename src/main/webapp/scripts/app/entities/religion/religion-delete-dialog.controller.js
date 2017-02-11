'use strict';

angular.module('stepApp')
	.controller('ReligionDeleteController',
    ['$scope', '$modalInstance', 'entity', 'Religion',
    function($scope, $modalInstance, entity, Religion) {

        $scope.religion = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Religion.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
