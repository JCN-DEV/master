'use strict';

angular.module('stepApp')
	.controller('InstFinancialInfoDeleteController',
    ['$scope','$rootScope', '$modalInstance', 'entity', 'InstFinancialInfo',
    function($scope,$rootScope, $modalInstance, entity, InstFinancialInfo) {

        $scope.instFinancialInfo = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            InstFinancialInfo.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
            $rootScope.setErrorMessage('stepApp.InstFinancialInfo.deleted');
        };

    }]);
