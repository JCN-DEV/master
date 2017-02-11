'use strict';

angular.module('stepApp')
    .controller('PrlEconomicalCodeInfoDetailController', function ($scope, $rootScope, $stateParams, entity, PrlEconomicalCodeInfo, PrlAllowDeductInfo) {
        $scope.prlEconomicalCodeInfo = entity;
        $scope.load = function (id) {
            PrlEconomicalCodeInfo.get({id: id}, function(result) {
                $scope.prlEconomicalCodeInfo = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:prlEconomicalCodeInfoUpdate', function(event, result) {
            $scope.prlEconomicalCodeInfo = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
