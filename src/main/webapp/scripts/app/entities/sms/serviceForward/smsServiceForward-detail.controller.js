'use strict';

angular.module('stepApp')
    .controller('SmsServiceForwardDetailController',
     ['$scope', '$rootScope', '$stateParams', 'entity', 'SmsServiceForward', 'SmsServiceComplaint', 'User', 'SmsServiceDepartment',
     function ($scope, $rootScope, $stateParams, entity, SmsServiceForward, SmsServiceComplaint, User, SmsServiceDepartment) {
        $scope.smsServiceForward = entity;
        $scope.load = function (id) {
            SmsServiceForward.get({id: id}, function(result) {
                $scope.smsServiceForward = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:smsServiceForwardUpdate', function(event, result) {
            $scope.smsServiceForward = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
