'use strict';

angular.module('stepApp')
	.controller('SmsServiceForwardDeleteController',
	 ['$scope', '$modalInstance', 'entity', 'SmsServiceForward',
	 function($scope, $modalInstance, entity, SmsServiceForward) {

        $scope.smsServiceForward = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            SmsServiceForward.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
