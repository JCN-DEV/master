'use strict';

angular.module('stepApp')
	.controller('InstEmpSpouseInfoDeleteController',
	['$scope', '$modalInstance', 'entity', 'InstEmpSpouseInfo',
	 function($scope, $modalInstance, entity, InstEmpSpouseInfo) {

        $scope.instEmpSpouseInfo = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            InstEmpSpouseInfo.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
            $rootScope.setErrorMessage('stepApp.InstEmpSpouseInfo.deleted');
        };

    }]);
