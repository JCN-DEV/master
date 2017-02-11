'use strict';

angular.module('stepApp')
    .controller('InstLandTempDetailController', function ($scope, $rootScope, $stateParams, entity, InstLandTemp) {
        $scope.instLandTemp = entity;
        $scope.load = function (id) {
            InstLandTemp.get({id: id}, function(result) {
                $scope.instLandTemp = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:instLandTempUpdate', function(event, result) {
            $scope.instLandTemp = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
