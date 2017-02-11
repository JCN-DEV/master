'use strict';

angular.module('stepApp')
    .controller('DeoHistLogController', function ($scope, $state, $modal, DeoHistLog, DeoHistLogSearch, ParseLinks) {
      
        $scope.deoHistLogs = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            DeoHistLog.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.deoHistLogs = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            DeoHistLogSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.deoHistLogs = result;
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
            $scope.deoHistLog = {
                dateCrated: null,
                dateModified: null,
                status: null,
                activated: null,
                id: null
            };
        };
    });
