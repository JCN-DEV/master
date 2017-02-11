'use strict';

angular.module('stepApp')
	.controller('InstEmployeeDeleteController',
	['$scope','$modalInstance','entity','InstEmployee',
	 function($scope, $modalInstance, entity, InstEmployee) {

        $scope.instEmployee = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            InstEmployee.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
