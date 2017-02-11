'use strict';

angular.module('stepApp')
	.controller('InstAdmInfoDeleteController',
	['$scope','$modalInstance','entity','InstAdmInfo',
	 function($scope, $modalInstance, entity, InstAdmInfo) {

        $scope.instAdmInfo = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            InstAdmInfo.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
