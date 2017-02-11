'use strict';

angular.module('stepApp')
	.controller('HrSpouseInfoDeleteController',
	 ['$scope', '$rootScope', '$modalInstance', 'entity', 'HrSpouseInfo',
	 function($scope, $rootScope, $modalInstance, entity, HrSpouseInfo) {

        $scope.hrSpouseInfo = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            HrSpouseInfo.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
            $rootScope.setErrorMessage('stepApp.HrSpouseInfo.deleted');
        };

    }]);
