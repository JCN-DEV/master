'use strict';

angular.module('stepApp')
	.controller('SmsServiceTypeDeleteController',
	 ['$scope', '$modalInstance', 'entity', 'SmsServiceType',
	 function($scope, $modalInstance, entity, SmsServiceType) {

        $scope.smsServiceType = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            SmsServiceType.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
