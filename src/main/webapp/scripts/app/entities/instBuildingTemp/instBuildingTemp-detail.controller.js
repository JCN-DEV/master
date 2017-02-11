'use strict';

angular.module('stepApp')
    .controller('InstBuildingTempDetailController', function ($scope, $rootScope, $stateParams, entity, InstBuildingTemp) {
        $scope.instBuildingTemp = entity;
        $scope.load = function (id) {
            InstBuildingTemp.get({id: id}, function(result) {
                $scope.instBuildingTemp = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:instBuildingTempUpdate', function(event, result) {
            $scope.instBuildingTemp = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
