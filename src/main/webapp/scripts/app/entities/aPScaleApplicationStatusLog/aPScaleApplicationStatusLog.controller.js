'use strict';

angular.module('stepApp')
    .controller('APScaleApplicationStatusLogController',
    ['$scope', '$state', '$modal', 'APScaleApplicationStatusLog', 'APScaleApplicationStatusLogSearch', 'ParseLinks',
    function ($scope, $state, $modal, APScaleApplicationStatusLog, APScaleApplicationStatusLogSearch, ParseLinks) {

        $scope.aPScaleApplicationStatusLogs = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            APScaleApplicationStatusLog.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.aPScaleApplicationStatusLogs = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            APScaleApplicationStatusLogSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.aPScaleApplicationStatusLogs = result;
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
            $scope.aPScaleApplicationStatusLog = {
                status: null,
                remarks: null,
                fromDate: null,
                toDate: null,
                cause: null,
                id: null
            };
        };
    }]);
