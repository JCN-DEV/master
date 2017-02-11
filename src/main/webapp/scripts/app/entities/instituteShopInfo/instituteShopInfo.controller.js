'use strict';

angular.module('stepApp')
    .controller('InstituteShopInfoController',
    ['$scope', '$state', '$modal', 'InstituteShopInfo', 'InstituteShopInfoSearch', 'ParseLinks',
    function ($scope, $state, $modal, InstituteShopInfo, InstituteShopInfoSearch, ParseLinks) {

        $scope.instituteShopInfos = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            InstituteShopInfo.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.instituteShopInfos = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            InstituteShopInfoSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.instituteShopInfos = result;
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
            $scope.instituteShopInfo = {
                nameOrNumber: null,
                buildingNameOrNumber: null,
                length: null,
                width: null,
                id: null
            };
        };
    }]);
