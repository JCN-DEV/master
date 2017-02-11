'use strict';

angular.module('stepApp')
    .controller('MiscConfigSetupDetailController', function ($scope, $rootScope, $stateParams, entity, MiscConfigSetup) {
        $scope.miscConfigSetup = entity;
        $scope.load = function (id) {
            MiscConfigSetup.get({id: id}, function(result) {
                $scope.miscConfigSetup = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:miscConfigSetupUpdate', function(event, result) {
            $scope.miscConfigSetup = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
