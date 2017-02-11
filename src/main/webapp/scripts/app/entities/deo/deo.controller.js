'use strict';

angular.module('stepApp')
    .controller('DeoController', function ($scope, $state, $modal, Deo, DeoSearch, ParseLinks, ActiveDeoHistLogs) {

        $scope.deos = [];
        $scope.deoHistLogs = ActiveDeoHistLogs.query();;
        $scope.page = 0;
        ActiveDeoHistLogs.query();
        $scope.loadAll = function() {
            Deo.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.deos = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            DeoSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.deos = result;
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
            $scope.deo = {
                contactNo: null,
                name: null,
                designation: null,
                orgName: null,
                dateCrated: null,
                dateModified: null,
                status: null,
                email: null,
                activated: null,
                id: null
            };
        };
    });
