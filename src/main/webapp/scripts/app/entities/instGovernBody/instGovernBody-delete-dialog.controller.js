'use strict';

angular.module('stepApp')
	.controller('InstGovernBodyDeleteController',
    ['$scope','$modalInstance','entity','InstGovernBody',
    function($scope, $modalInstance, entity, InstGovernBody) {

        $scope.instGovernBody = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            InstGovernBody.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
