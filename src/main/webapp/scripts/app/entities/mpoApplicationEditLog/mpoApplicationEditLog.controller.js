'use strict';

angular.module('stepApp')
    .controller('MpoApplicationEditLogController',
    ['$scope','$state','$modal','MpoApplicationEditLog','MpoApplicationEditLogSearch','ParseLinks',
    function ($scope, $state, $modal, MpoApplicationEditLog, MpoApplicationEditLogSearch, ParseLinks) {

        $scope.mpoApplicationEditLogs = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            MpoApplicationEditLog.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.mpoApplicationEditLogs = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            MpoApplicationEditLogSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.mpoApplicationEditLogs = result;
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
            $scope.mpoApplicationEditLog = {
                status: null,
                remarks: null,
                date: null,
                id: null
            };
        };
    }]);
