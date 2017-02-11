'use strict';

angular.module('stepApp')
    .controller('DesignationController',
    ['$scope', '$state', '$modal', 'Designation', 'DesignationSearch', 'ParseLinks',
    function ($scope, $state, $modal, Designation, DesignationSearch, ParseLinks) {

        $scope.designations = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Designation.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.designations = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            DesignationSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.designations = result;
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
            $scope.designation = {
                code: null,
                name: null,
                description: null,
                type: null,
                status: null,
                id: null
            };
        };
    }]);
