'use strict';

angular.module('stepApp')
    .controller('InstAcaInfoTempDetailController', function ($scope, $rootScope, $stateParams, entity, InstAcaInfoTemp, Institute) {
        $scope.instAcaInfoTemp = entity;
        $scope.load = function (id) {
            InstAcaInfoTemp.get({id: id}, function(result) {
                $scope.instAcaInfoTemp = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:instAcaInfoTempUpdate', function(event, result) {
            $scope.instAcaInfoTemp = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
