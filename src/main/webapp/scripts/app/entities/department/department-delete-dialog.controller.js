'use strict';

angular.module('stepApp')
	.controller('DepartmentDeleteController',
	['$scope', '$modalInstance', 'entity', 'Department',
	function($scope, $modalInstance, entity, Department) {

        $scope.department = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Department.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
