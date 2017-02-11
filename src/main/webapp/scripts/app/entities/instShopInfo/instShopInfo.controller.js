'use strict';

angular.module('stepApp')
    .controller('InstShopInfoController',
    ['$scope', '$state', '$modal', 'InstShopInfo', 'InstShopInfoSearch', 'ParseLinks',
    function ($scope, $state, $modal, InstShopInfo, InstShopInfoSearch, ParseLinks) {

        $scope.instShopInfos = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            InstShopInfo.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.instShopInfos = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            InstShopInfoSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.instShopInfos = result;
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
            $scope.instShopInfo = {
                nameOrNumber: null,
                buildingNameOrNumber: null,
                length: null,
                width: null,
                id: null
            };
        };
    }]);
