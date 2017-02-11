'use strict';

angular.module('stepApp')
    .controller('TimeScaleApplicationStatusLogController',
    ['$scope', '$state', '$modal', 'TimeScaleApplicationStatusLog', 'TimeScaleApplicationStatusLogSearch', 'ParseLinks',
    function ($scope, $state, $modal, TimeScaleApplicationStatusLog, TimeScaleApplicationStatusLogSearch, ParseLinks) {

        $scope.timeScaleApplicationStatusLogs = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            TimeScaleApplicationStatusLog.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.timeScaleApplicationStatusLogs = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            TimeScaleApplicationStatusLogSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.timeScaleApplicationStatusLogs = result;
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
            $scope.timeScaleApplicationStatusLog = {
                status: null,
                remarks: null,
                fromDate: null,
                toDate: null,
                cause: null,
                id: null
            };
        };
    }]);
