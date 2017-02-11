'use strict';

angular.module('stepApp')
    .controller('PgmsNotificationDetailController', function ($scope, $rootScope, $stateParams, entity, PgmsNotification) {
        $scope.pgmsNotification = entity;
        $scope.load = function (id) {
            PgmsNotification.get({id: id}, function(result) {
                $scope.pgmsNotification = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:pgmsNotificationUpdate', function(event, result) {
            $scope.pgmsNotification = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
