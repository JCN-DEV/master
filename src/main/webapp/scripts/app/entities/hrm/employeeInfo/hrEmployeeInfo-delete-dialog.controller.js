'use strict';

angular.module('stepApp')
	.controller('HrEmployeeInfoDeleteController',
	 ['$scope', '$rootScope', '$modalInstance', 'entity', 'HrEmployeeInfo',
	 function($scope, $rootScope, $modalInstance, entity, HrEmployeeInfo) {

        $scope.hrEmployeeInfo = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            HrEmployeeInfo.delete({id: id},
                function () {
                    $modalInstance.close(true);
                    $rootScope.setErrorMessage('stepApp.hrEmployeeInfo.deleted');
                });
        };

    }]);
