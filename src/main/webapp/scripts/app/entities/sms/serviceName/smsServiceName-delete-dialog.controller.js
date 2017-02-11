'use strict';

angular.module('stepApp')
	.controller('SmsServiceNameDeleteController',
	 ['$scope', '$modalInstance', 'entity', 'SmsServiceName',
	 function($scope, $modalInstance, entity, SmsServiceName) {

        $scope.smsServiceName = entity;
        $scope.clear = function() {
        	$modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            SmsServiceName.delete({id: id},
                function () {
            		$modalInstance.close(true);
                });
        };

    }]);
