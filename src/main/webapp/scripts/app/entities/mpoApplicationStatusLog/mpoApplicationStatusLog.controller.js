'use strict';

angular.module('stepApp')
    .controller('MpoApplicationStatusLogController',
    ['$scope','$state','$modal','MpoApplicationStatusLog','MpoApplicationStatusLogSearch','ParseLinks',
    function ($scope, $state, $modal, MpoApplicationStatusLog, MpoApplicationStatusLogSearch, ParseLinks) {

        $scope.mpoApplicationStatusLogs = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            MpoApplicationStatusLog.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.mpoApplicationStatusLogs = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            MpoApplicationStatusLogSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.mpoApplicationStatusLogs = result;
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
            $scope.mpoApplicationStatusLog = {
                status: null,
                remarks: null,
                fromDate: null,
                toDate: null,
                id: null
            };
        };
    }]);
