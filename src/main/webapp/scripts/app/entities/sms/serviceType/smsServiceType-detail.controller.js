'use strict';

angular.module('stepApp')
    .controller('SmsServiceTypeDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'SmsServiceType',
    function ($scope, $rootScope, $stateParams, entity, SmsServiceType) {
        $scope.smsServiceType = entity;
        $scope.load = function (id) {
            SmsServiceType.get({id: id}, function(result) {
                $scope.smsServiceType = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:smsServiceTypeUpdate', function(event, result) {
            $scope.smsServiceType = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
