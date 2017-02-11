'use strict';

angular.module('stepApp')
    .controller('ProfessorApplicationEditLogController',
    ['$scope', '$state', '$modal', 'ProfessorApplicationEditLog', 'ProfessorApplicationEditLogSearch', 'ParseLinks',
    function ($scope, $state, $modal, ProfessorApplicationEditLog, ProfessorApplicationEditLogSearch, ParseLinks) {

        $scope.professorApplicationEditLogs = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            ProfessorApplicationEditLog.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.professorApplicationEditLogs = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            ProfessorApplicationEditLogSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.professorApplicationEditLogs = result;
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
            $scope.professorApplicationEditLog = {
                status: null,
                remarks: null,
                date: null,
                id: null
            };
        };
    }]);
