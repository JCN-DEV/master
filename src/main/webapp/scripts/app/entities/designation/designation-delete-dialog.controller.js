'use strict';

angular.module('stepApp')
	.controller('DesignationDeleteController',
	['$scope', '$modalInstance', 'entity', 'Designation',
	function($scope, $modalInstance, entity, Designation) {

        $scope.designation = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Designation.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
