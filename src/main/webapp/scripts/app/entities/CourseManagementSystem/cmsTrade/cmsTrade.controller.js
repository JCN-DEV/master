'use strict';

angular.module('stepApp')
    .controller('CmsTradeController',
        ['$scope', '$state', '$modal', 'CmsTrade', 'CmsTradeSearch', 'ParseLinks',
        function ($scope, $state, $modal, CmsTrade, CmsTradeSearch, ParseLinks) {

        $scope.cmsTrades = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            CmsTrade.query({page: $scope.page, size: 2000}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.cmsTrades = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            CmsTradeSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.cmsTrades = result;
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
            $scope.cmsTrade = {
                code: null,
                name: null,
                description: null,
                status: null,
                id: null
            };
        };
    }]);
