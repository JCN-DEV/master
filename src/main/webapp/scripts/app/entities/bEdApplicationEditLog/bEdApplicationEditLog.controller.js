'use strict';

angular.module('stepApp')
    .controller('BEdApplicationEditLogController',
    ['$scope', '$state', '$modal', 'BEdApplicationEditLog', 'BEdApplicationEditLogSearch', 'ParseLinks',
     function ($scope, $state, $modal, BEdApplicationEditLog, BEdApplicationEditLogSearch, ParseLinks) {

        $scope.bEdApplicationEditLogs = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            BEdApplicationEditLog.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.bEdApplicationEditLogs = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            BEdApplicationEditLogSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.bEdApplicationEditLogs = result;
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
            $scope.bEdApplicationEditLog = {
                status: null,
                remarks: null,
                created_date: null,
                id: null
            };
        };
    }]);
