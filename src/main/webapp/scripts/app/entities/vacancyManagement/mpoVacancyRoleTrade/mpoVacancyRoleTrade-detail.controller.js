'use strict';

angular.module('stepApp')
    .controller('MpoVacancyRoleTradeDetailController', function ($scope, $rootScope, $stateParams, entity, MpoVacancyRoleTrade, MpoVacancyRole, User, CmsTrade) {
        $scope.mpoVacancyRoleTrade = entity;
        $scope.load = function (id) {
            MpoVacancyRoleTrade.get({id: id}, function(result) {
                $scope.mpoVacancyRoleTrade = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:mpoVacancyRoleTradeUpdate', function(event, result) {
            $scope.mpoVacancyRoleTrade = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
