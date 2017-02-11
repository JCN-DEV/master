'use strict';

angular.module('stepApp')
    .controller('MpoVacancyRoleDetailController', function ($scope, $rootScope, $stateParams, entity, MpoVacancyRole, MpoVacancyRoleDesgnationsByRole, MpoVacancyRoleTradeByVacancyRole) {
        $scope.mpoVacancyRole = entity;
        $scope.mpoTrades = [];
        $scope.mpoVacancyRoleDesgnations = [];

        MpoVacancyRoleDesgnationsByRole.query({id :$stateParams.id}, function(result){
            $scope.mpoVacancyRoleDesgnations = result;
        });

        MpoVacancyRoleTradeByVacancyRole.query({id :$stateParams.id}, function(result){
         $scope.mpoTrades = result;
         });

        $scope.load = function (id) {
            MpoVacancyRole.get({id: id}, function(result) {
                $scope.mpoVacancyRole = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:mpoVacancyRoleUpdate', function(event, result) {
            $scope.mpoVacancyRole = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
