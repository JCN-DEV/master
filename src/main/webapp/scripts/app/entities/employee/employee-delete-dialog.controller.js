'use strict';

angular.module('stepApp')
	.controller('EmployeeDeleteController',
	['$scope', '$modalInstance', 'entity', 'Employee',
	function($scope, $modalInstance, entity, Employee) {

        $scope.employee = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Employee.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
