'use strict';

angular.module('stepApp')
    .controller('DlContCatSetController',
    ['$scope', '$state', '$modal', 'DlContCatSet', 'DlContCatSetSearch', 'ParseLinks',
    function ($scope, $state, $modal, DlContCatSet, DlContCatSetSearch, ParseLinks) {

        $scope.dlContCatSets = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            DlContCatSet.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.dlContCatSets = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            DlContCatSetSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.dlContCatSets = result;
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
            $scope.dlContCatSet = {
                code: null,
                name: null,
                description: null,
                pStatus: null,
                createdDate: null,
                updatedDate: null,
                createdBy: null,
                updatedBy: null,
                status: null,
                id: null
            };
        };
    }]);
