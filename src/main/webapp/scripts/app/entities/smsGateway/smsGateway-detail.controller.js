'use strict';

angular.module('stepApp')
    .controller('SmsGatewayDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'SmsGateway',
    function ($scope, $rootScope, $stateParams, entity, SmsGateway) {
        $scope.smsGateway = entity;
        $scope.load = function (id) {
            SmsGateway.get({id: id}, function(result) {
                $scope.smsGateway = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:smsGatewayUpdate', function(event, result) {
            $scope.smsGateway = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
