'use strict';

angular.module('stepApp')
    .controller('SmsServiceNameDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'SmsServiceName',
    function ($scope, $rootScope, $stateParams, entity, SmsServiceName) {
        $scope.smsServiceName = entity;
        $scope.load = function (id) {
            SmsServiceName.get({id: id}, function(result) {
                $scope.smsServiceName = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:smsServiceNameUpdate', function(event, result) {
            $scope.smsServiceName = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
