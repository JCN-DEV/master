'use strict';

angular.module('stepApp')
    .controller('SmsServiceAssignDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'SmsServiceAssign', 'SmsServiceDepartment', 'Employee', 'User',
    function ($scope, $rootScope, $stateParams, entity, SmsServiceAssign, SmsServiceDepartment, Employee, User) {
        $scope.smsServiceAssign = entity;
        $scope.load = function (id) {
            SmsServiceAssign.get({id: id}, function(result) {
                $scope.smsServiceAssign = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:smsServiceAssignUpdate', function(event, result) {
            $scope.smsServiceAssign = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
