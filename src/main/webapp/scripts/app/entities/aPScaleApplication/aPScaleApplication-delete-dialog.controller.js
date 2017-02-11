'use strict';

angular.module('stepApp')
	.controller('APScaleApplicationDeleteController',
	['$scope', '$modalInstance', 'entity', 'APScaleApplication',
	function($scope, $modalInstance, entity, APScaleApplication) {

        $scope.aPScaleApplication = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            APScaleApplication.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
