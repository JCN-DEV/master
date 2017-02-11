'use strict';

angular.module('stepApp')
    .controller('InstLevelTempDetailController', function ($scope, $rootScope, $stateParams, entity, InstLevelTemp) {
        $scope.instLevelTemp = entity;
        $scope.load = function (id) {
            InstLevelTemp.get({id: id}, function(result) {
                $scope.instLevelTemp = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:instLevelTempUpdate', function(event, result) {
            $scope.instLevelTemp = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
