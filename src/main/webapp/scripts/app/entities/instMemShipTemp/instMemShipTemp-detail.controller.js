'use strict';

angular.module('stepApp')
    .controller('InstMemShipTempDetailController', function ($scope, $rootScope, $stateParams, entity, InstMemShipTemp) {
        $scope.instMemShipTemp = entity;
        $scope.load = function (id) {
            InstMemShipTemp.get({id: id}, function(result) {
                $scope.instMemShipTemp = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:instMemShipTempUpdate', function(event, result) {
            $scope.instMemShipTemp = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
