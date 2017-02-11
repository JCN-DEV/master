'use strict';

angular.module('stepApp')
    .controller('MpoVacancyRoleTradeController', function ($scope, $state, $modal, MpoVacancyRoleTrade, MpoVacancyRoleTradeSearch, ParseLinks) {
      
        $scope.mpoVacancyRoleTrades = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            MpoVacancyRoleTrade.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.mpoVacancyRoleTrades = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            MpoVacancyRoleTradeSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.mpoVacancyRoleTrades = result;
            }, function(response) {
                if(response.status === 404) {
                    $scope.loadAll();
                }
            });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.mpoVacancyRoleTrade = {
                crateDate: null,
                updateDate: null,
                status: null,
                id: null
            };
        };
    });
