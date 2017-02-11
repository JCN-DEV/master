'use strict';

angular.module('stepApp')
    .controller('SmsServiceReplyDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'SmsServiceReply', 'SmsServiceComplaint', 'User',
    function ($scope, $rootScope, $stateParams, entity, SmsServiceReply, SmsServiceComplaint, User) {
        $scope.smsServiceReply = entity;
        $scope.load = function (id) {
            SmsServiceReply.get({id: id}, function(result) {
                $scope.smsServiceReply = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:smsServiceReplyUpdate', function(event, result) {
            $scope.smsServiceReply = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
