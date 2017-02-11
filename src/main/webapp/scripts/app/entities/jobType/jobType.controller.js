'use strict';

angular.module('stepApp')
    .controller('JobTypeController',
     ['$scope', '$state', '$modal', 'JobType', 'JobTypeSearch', 'ParseLinks',
     function ($scope, $state, $modal, JobType, JobTypeSearch, ParseLinks) {

        $scope.jobTypes = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            JobType.query({page: $scope.page, size: 5000}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.jobTypes = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            JobTypeSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.jobTypes = result;
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
            $scope.jobType = {
                name: null,
                description: null,
                status: null,
                id: null
            };
        };
    }]);
