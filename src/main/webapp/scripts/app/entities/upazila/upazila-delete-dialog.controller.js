'use strict';

angular.module('stepApp')
	.controller('UpazilaDeleteController',
	['$scope', '$modalInstance', 'entity', 'Upazila',
	function($scope, $modalInstance, entity, Upazila) {

        $scope.upazila = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Upazila.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);

