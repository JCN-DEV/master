'use strict';

angular.module('stepApp')
	.controller('InstEmpCountDeleteController',
    ['$scope', '$rootScope', '$modalInstance', 'entity', 'InstEmpCount',
    function($scope, $rootScope, $modalInstance, entity, InstEmpCount) {

        $scope.instEmpCount = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            InstEmpCount.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
            $rootScope.setErrorMessage('stepApp.instEmpCount.deleted');

        };

    }]);
