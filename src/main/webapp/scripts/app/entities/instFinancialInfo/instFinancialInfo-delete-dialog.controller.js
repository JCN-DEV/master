'use strict';

angular.module('stepApp')
	.controller('InstFinancialInfoDeleteController',
    ['$scope','$modalInstance','entity','InstFinancialInfo',
    function($scope, $modalInstance, entity, InstFinancialInfo) {

        $scope.instFinancialInfo = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            InstFinancialInfo.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
