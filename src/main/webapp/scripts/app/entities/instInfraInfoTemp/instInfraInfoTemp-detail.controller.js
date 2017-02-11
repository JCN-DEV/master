'use strict';

angular.module('stepApp')
    .controller('InstInfraInfoTempDetailController', function ($scope, $rootScope, $stateParams, entity, InstInfraInfoTemp, Institute, InstBuilding, InstLand) {
        $scope.instInfraInfoTemp = entity;
        $scope.load = function (id) {
            InstInfraInfoTemp.get({id: id}, function(result) {
                $scope.instInfraInfoTemp = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:instInfraInfoTempUpdate', function(event, result) {
            $scope.instInfraInfoTemp = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
