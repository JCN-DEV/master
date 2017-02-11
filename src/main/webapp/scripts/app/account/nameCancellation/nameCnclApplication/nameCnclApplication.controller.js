'use strict';

angular.module('stepApp')
    .controller('NameCnclApplicationController',
    ['$scope', '$state', '$modal', 'NameCnclApplication', 'NameCnclApplicationSearch', 'ParseLinks',
    function ($scope, $state, $modal, NameCnclApplication, NameCnclApplicationSearch, ParseLinks) {

        $scope.nameCnclApplications = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            NameCnclApplication.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.nameCnclApplications = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            NameCnclApplicationSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.nameCnclApplications = result;
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
            $scope.nameCnclApplication = {
                created_date: null,
                status: null,
                id: null
            };
        };
    }]);
