'use strict';

angular.module('stepApp')
    .controller('DivisionController',
    ['$scope', '$state', '$modal', 'Division', 'DivisionSearch', 'ParseLinks',
    function ($scope, $state, $modal, Division, DivisionSearch, ParseLinks) {

        $scope.divisions = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Division.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.divisions = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            DivisionSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.divisions = result;
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
            $scope.division = {
                name: null,
                bnName: null,
                id: null
            };
        };
    }]);
