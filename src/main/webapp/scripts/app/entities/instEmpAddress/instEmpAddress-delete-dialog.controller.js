'use strict';

angular.module('stepApp')
	.controller('InstEmpAddressDeleteController',
	['$scope','$modalInstance','entity','InstEmpAddress',
	function($scope, $modalInstance, entity, InstEmpAddress) {

        $scope.instEmpAddress = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            InstEmpAddress.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
