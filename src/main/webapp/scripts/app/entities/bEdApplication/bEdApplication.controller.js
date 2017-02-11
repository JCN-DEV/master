'use strict';

angular.module('stepApp')
    .controller('BEdApplicationController',
     ['$scope', '$state', '$modal', 'BEdApplication', 'BEdApplicationSearch', 'ParseLinks',
     function ($scope, $state, $modal, BEdApplication, BEdApplicationSearch, ParseLinks) {

        $scope.bEdApplications = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            BEdApplication.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.bEdApplications = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            BEdApplicationSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.bEdApplications = result;
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
            $scope.bEdApplication = {
                created_date: null,
                status: null,
                id: null
            };
        };
    }]);
