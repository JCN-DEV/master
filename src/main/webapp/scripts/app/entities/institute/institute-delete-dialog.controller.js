'use strict';

angular.module('stepApp')
	.controller('InstituteDeleteController',
    ['$scope','$modalInstance','entity','Institute',
    function($scope, $modalInstance, entity, Institute) {

        $scope.institute = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Institute.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
