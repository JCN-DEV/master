'use strict';

angular.module('stepApp')
    .controller('InstPlayGroundInfoController',
    ['$scope', '$state', '$modal', 'InstPlayGroundInfo', 'InstPlayGroundInfoSearch', 'ParseLinks',
    function ($scope, $state, $modal, InstPlayGroundInfo, InstPlayGroundInfoSearch, ParseLinks) {

        $scope.instPlayGroundInfos = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            InstPlayGroundInfo.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.instPlayGroundInfos = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            InstPlayGroundInfoSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.instPlayGroundInfos = result;
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
            $scope.instPlayGroundInfo = {
                playgroundNo: null,
                area: null,
                id: null
            };
        };
    }]);
