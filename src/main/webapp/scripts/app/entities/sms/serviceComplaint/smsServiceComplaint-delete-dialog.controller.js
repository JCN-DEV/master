'use strict';

angular.module('stepApp')
	.controller('SmsServiceComplaintDeleteController',
	['$scope', '$modalInstance', 'entity', 'SmsServiceComplaint',
	function($scope, $modalInstance, entity, SmsServiceComplaint) {

        $scope.smsServiceComplaint = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            SmsServiceComplaint.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
