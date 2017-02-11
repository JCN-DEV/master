'use strict';

angular.module('stepApp')
    .controller('DlContUpldController',
    ['$scope', '$state', '$modal', 'DlContUpld', 'DlContUpldSearch', 'ParseLinks',
     function ($scope, $state, $modal, DlContUpld, DlContUpldSearch, ParseLinks) {

        $scope.dlContUplds = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            DlContUpld.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.dlContUplds = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            DlContUpldSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.dlContUplds = result;
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
            $scope.dlContUpld = {
                code: null,
                authName: null,
                edition: null,
                isbnNo: null,
                copyright: null,
                publisher: null,
                createdDate: null,
                updatedDate: null,
                createdBy: null,
                updatedBy: null,
                status: null,
                id: null
            };
        };
    }]);
