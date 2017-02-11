'use strict';

angular.module('stepApp')
    .controller('InstFinancialInfoTempDetailController', function ($scope, $rootScope, $stateParams, entity, InstFinancialInfoTemp) {
        $scope.instFinancialInfoTemp = entity;
        $scope.load = function (id) {
            InstFinancialInfoTemp.get({id: id}, function(result) {
                $scope.instFinancialInfoTemp = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:instFinancialInfoTempUpdate', function(event, result) {
            $scope.instFinancialInfoTemp = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
