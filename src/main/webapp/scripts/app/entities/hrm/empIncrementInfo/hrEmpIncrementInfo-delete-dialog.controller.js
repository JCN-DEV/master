'use strict';

angular.module('stepApp')
	.controller('HrEmpIncrementInfoDeleteController',
	 ['$scope','$rootScope', '$modalInstance', 'entity', 'HrEmpIncrementInfo',
	 function($scope,$rootScope, $modalInstance, entity, HrEmpIncrementInfo) {

        $scope.hrEmpIncrementInfo = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            HrEmpIncrementInfo.delete({id: id},
                function () {
                    $modalInstance.close(true);
                    $rootScope.setErrorMessage('stepApp.hrEmpIncrementInfo.deleted');
                });
        };

    }]);
