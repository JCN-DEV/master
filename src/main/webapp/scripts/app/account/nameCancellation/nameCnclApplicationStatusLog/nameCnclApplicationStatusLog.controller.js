'use strict';

angular.module('stepApp')
    .controller('NameCnclApplicationStatusLogController', function ($scope, $state, $modal, NameCnclApplicationStatusLog, NameCnclApplicationStatusLogSearch, ParseLinks) {
      
        $scope.nameCnclApplicationStatusLogs = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            NameCnclApplicationStatusLog.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.nameCnclApplicationStatusLogs = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            NameCnclApplicationStatusLogSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.nameCnclApplicationStatusLogs = result;
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
            $scope.nameCnclApplicationStatusLog = {
                status: null,
                remarks: null,
                fromDate: null,
                toDate: null,
                cause: null,
                id: null
            };
        };
    });
