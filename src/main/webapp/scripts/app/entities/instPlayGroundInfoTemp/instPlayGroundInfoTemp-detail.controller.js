'use strict';

angular.module('stepApp')
    .controller('InstPlayGroundInfoTempDetailController', function ($scope, $rootScope, $stateParams, entity, InstPlayGroundInfoTemp, InstInfraInfo) {
        $scope.instPlayGroundInfoTemp = entity;
        $scope.load = function (id) {
            InstPlayGroundInfoTemp.get({id: id}, function(result) {
                $scope.instPlayGroundInfoTemp = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:instPlayGroundInfoTempUpdate', function(event, result) {
            $scope.instPlayGroundInfoTemp = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
