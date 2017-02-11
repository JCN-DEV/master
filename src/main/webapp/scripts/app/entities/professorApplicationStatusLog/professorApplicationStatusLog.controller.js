'use strict';

angular.module('stepApp')
    .controller('ProfessorApplicationStatusLogController',
     ['$scope', '$state', '$modal', 'ProfessorApplicationStatusLog', 'ProfessorApplicationStatusLogSearch', 'ParseLinks',
     function ($scope, $state, $modal, ProfessorApplicationStatusLog, ProfessorApplicationStatusLogSearch, ParseLinks) {

        $scope.professorApplicationStatusLogs = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            ProfessorApplicationStatusLog.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.professorApplicationStatusLogs = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            ProfessorApplicationStatusLogSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.professorApplicationStatusLogs = result;
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
            $scope.professorApplicationStatusLog = {
                status: null,
                remarks: null,
                fromDate: null,
                toDate: null,
                cause: null,
                id: null
            };
        };
    }]);
