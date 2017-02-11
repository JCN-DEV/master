'use strict';

angular.module('stepApp')
    .controller('NotificationStepDetailController', function ( $scope, $rootScope, $stateParams, entity, NotificationStep) {
        $scope.notificationStep = entity;
        $scope.load = function (id) {
            NotificationStep.get({id: id}, function(result) {
                $scope.notificationStep = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:notificationStepUpdate', function(event, result) {
            $scope.notificationStep = result;
        });
        $scope.$on('$destroy', unsubscribe);
        $rootScope.setErrorMessage('stepApp.notificationStepUpdate.deleted');
    });
