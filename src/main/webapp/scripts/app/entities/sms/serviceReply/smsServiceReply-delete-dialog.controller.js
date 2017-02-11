'use strict';

angular.module('stepApp')
	.controller('SmsServiceReplyDeleteController',
	 ['$scope', '$modalInstance', 'entity', 'SmsServiceReply',
	 function($scope, $modalInstance, entity, SmsServiceReply) {

        $scope.smsServiceReply = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            SmsServiceReply.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
