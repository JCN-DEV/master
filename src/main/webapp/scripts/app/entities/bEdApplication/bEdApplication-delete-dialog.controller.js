'use strict';

angular.module('stepApp')
	.controller('BEdApplicationDeleteController',
	 ['$scope', '$modalInstance', 'entity', 'BEdApplication',
	 function($scope, $modalInstance, entity, BEdApplication) {

        $scope.bEdApplication = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            BEdApplication.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
