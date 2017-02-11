'use strict';

angular.module('stepApp')
	.controller('SmsServiceAssignDeleteController',
    ['$scope', '$modalInstance', 'entity', 'SmsServiceAssign',
    function($scope, $modalInstance, entity, SmsServiceAssign) {

        $scope.smsServiceAssign = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            SmsServiceAssign.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
