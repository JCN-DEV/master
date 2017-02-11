'use strict';

angular.module('stepApp')
    .controller('BoardNameController',
    ['$scope', '$state', '$modal', 'BoardName', 'BoardNameSearch', 'ParseLinks',
    function ($scope, $state, $modal, BoardName, BoardNameSearch, ParseLinks) {

        $scope.boardNames = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            BoardName.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.boardNames = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            BoardNameSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.boardNames = result;
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
            $scope.boardName = {
                name: null,
                id: null
            };
        };
    }]);
