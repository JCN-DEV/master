'use strict';

angular.module('stepApp')
	.controller('SmsServiceDepartmentDeleteController',
	 ['$scope', '$modalInstance', 'entity', 'SmsServiceDepartment',
	 function($scope, $modalInstance, entity, SmsServiceDepartment) {

        $scope.smsServiceDepartment = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            SmsServiceDepartment.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
