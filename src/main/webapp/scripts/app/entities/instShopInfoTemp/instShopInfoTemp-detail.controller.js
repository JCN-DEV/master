'use strict';

angular.module('stepApp')
    .controller('InstShopInfoTempDetailController', function ($scope, $rootScope, $stateParams, entity, InstShopInfoTemp, InstInfraInfo) {
        $scope.instShopInfoTemp = entity;
        $scope.load = function (id) {
            InstShopInfoTemp.get({id: id}, function(result) {
                $scope.instShopInfoTemp = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:instShopInfoTempUpdate', function(event, result) {
            $scope.instShopInfoTemp = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
