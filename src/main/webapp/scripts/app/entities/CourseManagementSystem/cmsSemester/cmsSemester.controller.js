'use strict';

angular.module('stepApp')
    .controller('CmsSemesterController',
        ['$scope', '$state', '$modal', 'CmsSemester', 'CmsSemesterSearch', 'ParseLinks',
        function ($scope, $state, $modal, CmsSemester, CmsSemesterSearch, ParseLinks) {

        $scope.cmsSemesters = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            CmsSemester.query({page: $scope.page, size: 2000}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.cmsSemesters = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            CmsSemesterSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.cmsSemesters = result;
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
            $scope.cmsSemester = {
                code: null,
                name: null,
                year: null,
                duration: null,
                description: null,
                status: null,
                id: null
            };
        };
    }]);
