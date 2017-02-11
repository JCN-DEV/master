'use strict';

angular.module('stepApp')
    .controller('PrlPayscaleAllowanceInfoDetailController', function ($scope, $rootScope, $stateParams, entity, PrlPayscaleAllowanceInfo, PrlPayscaleInfo, PrlAllowDeductInfo) {
        $scope.prlPayscaleAllowanceInfo = entity;
        $scope.load = function (id) {
            PrlPayscaleAllowanceInfo.get({id: id}, function(result) {
                $scope.prlPayscaleAllowanceInfo = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:prlPayscaleAllowanceInfoUpdate', function(event, result) {
            $scope.prlPayscaleAllowanceInfo = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
