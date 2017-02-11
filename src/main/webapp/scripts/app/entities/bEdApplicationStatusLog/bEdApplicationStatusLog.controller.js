'use strict';

angular.module('stepApp')
    .controller('BEdApplicationStatusLogController',
    ['$scope', '$state', '$modal', 'BEdApplicationStatusLog', 'BEdApplicationStatusLogSearch', 'ParseLinks',
    function ($scope, $state, $modal, BEdApplicationStatusLog, BEdApplicationStatusLogSearch, ParseLinks) {

        $scope.bEdApplicationStatusLogs = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            BEdApplicationStatusLog.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.bEdApplicationStatusLogs = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            BEdApplicationStatusLogSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.bEdApplicationStatusLogs = result;
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
            $scope.bEdApplicationStatusLog = {
                status: null,
                remarks: null,
                fromDate: null,
                toDate: null,
                cause: null,
                id: null
            };
        };
    }]);
