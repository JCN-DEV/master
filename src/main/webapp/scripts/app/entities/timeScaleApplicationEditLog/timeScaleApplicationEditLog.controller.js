'use strict';

angular.module('stepApp')
    .controller('TimeScaleApplicationEditLogController',
    ['$scope', '$state', '$modal', 'TimeScaleApplicationEditLog', 'TimeScaleApplicationEditLogSearch', 'ParseLinks',
    function ($scope, $state, $modal, TimeScaleApplicationEditLog, TimeScaleApplicationEditLogSearch, ParseLinks) {

        $scope.timeScaleApplicationEditLogs = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            TimeScaleApplicationEditLog.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.timeScaleApplicationEditLogs = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            TimeScaleApplicationEditLogSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.timeScaleApplicationEditLogs = result;
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
            $scope.timeScaleApplicationEditLog = {
                status: null,
                remarks: null,
                date: null,
                id: null
            };
        };
    }]);
