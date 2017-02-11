'use strict';

angular.module('stepApp')
    .controller('MpoTradeController', function ($scope, $state, $modal, MpoTrade, MpoTradeSearch, ParseLinks) {
      
        $scope.mpoTrades = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            MpoTrade.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.mpoTrades = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            MpoTradeSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.mpoTrades = result;
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
            $scope.mpoTrade = {
                cratedDate: null,
                updateDate: null,
                status: null,
                id: null
            };
        };
    });
