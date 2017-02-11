'use strict';

angular.module('stepApp')
    .controller('DlContTypeSetController',
    ['$scope', '$state', '$modal', 'DlContTypeSet', 'DlContTypeSetSearch', 'ParseLinks',
    function ($scope, $state, $modal, DlContTypeSet, DlContTypeSetSearch, ParseLinks) {

        $scope.dlContTypeSets = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            DlContTypeSet.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.dlContTypeSets = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            DlContTypeSetSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.dlContTypeSets = result;
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
            $scope.dlContTypeSet = {
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