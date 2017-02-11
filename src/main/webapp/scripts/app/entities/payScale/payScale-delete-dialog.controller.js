'use strict';

angular.module('stepApp')
	.controller('PayScaleDeleteController',
    ['$scope','$state','$modalInstance','entity','PayScale',
    function($scope, $state, $modalInstance, entity, PayScale) {

        $scope.payScale = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            PayScale.delete({id: id},
                function () {
                    $modalInstance.close(true);
                    $state.go('payScale');
                });
        };

    }]);
