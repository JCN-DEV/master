'use strict';

angular.module('stepApp')
    .controller('InstLabInfoTempDetailController', function ($scope, $rootScope, $stateParams, entity, InstLabInfoTemp, InstInfraInfo) {
        $scope.instLabInfoTemp = entity;
        $scope.load = function (id) {
            InstLabInfoTemp.get({id: id}, function(result) {
                $scope.instLabInfoTemp = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:instLabInfoTempUpdate', function(event, result) {
            $scope.instLabInfoTemp = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
