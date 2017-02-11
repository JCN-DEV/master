'use strict';

angular.module('stepApp')
    .controller('NameCnclApplicationEditLogController',
     ['$scope', '$state', '$modal', 'NameCnclApplicationEditLog', 'NameCnclApplicationEditLogSearch', 'ParseLinks',
     function ($scope, $state, $modal, NameCnclApplicationEditLog, NameCnclApplicationEditLogSearch, ParseLinks) {

        $scope.nameCnclApplicationEditLogs = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            NameCnclApplicationEditLog.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.nameCnclApplicationEditLogs = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            NameCnclApplicationEditLogSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.nameCnclApplicationEditLogs = result;
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
            $scope.nameCnclApplicationEditLog = {
                status: null,
                remarks: null,
                created_date: null,
                id: null
            };
        };
    }]);
