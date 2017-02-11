'use strict';

angular.module('stepApp')
    .controller('PrlBudgetSetupInfoDetailController', function ($scope, $rootScope, $stateParams, entity, PrlBudgetSetupInfo, PrlEconomicalCodeInfo, PrlAllowDeductInfo) {
        $scope.prlBudgetSetupInfo = entity;
        $scope.load = function (id) {
            PrlBudgetSetupInfo.get({id: id}, function(result) {
                $scope.prlBudgetSetupInfo = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:prlBudgetSetupInfoUpdate', function(event, result) {
            $scope.prlBudgetSetupInfo = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
