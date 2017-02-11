'use strict';

angular.module('stepApp')
    .controller('APScaleApplicationEditLogController',
    ['$scope', '$state', '$modal', 'APScaleApplicationEditLog', 'APScaleApplicationEditLogSearch', 'ParseLinks',
    function ($scope, $state, $modal, APScaleApplicationEditLog, APScaleApplicationEditLogSearch, ParseLinks) {

        $scope.aPScaleApplicationEditLogs = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            APScaleApplicationEditLog.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.aPScaleApplicationEditLogs = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            APScaleApplicationEditLogSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.aPScaleApplicationEditLogs = result;
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
            $scope.aPScaleApplicationEditLog = {
                status: null,
                remarks: null,
                date: null,
                id: null
            };
        };
    }]);
