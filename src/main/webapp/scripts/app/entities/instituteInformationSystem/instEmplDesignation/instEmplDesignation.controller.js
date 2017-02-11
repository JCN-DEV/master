'use strict';

angular.module('stepApp')
    .controller('InstEmplDesignationController',
    ['$scope', '$state', '$modal', 'InstEmplDesignation', 'InstEmplDesignationSearch', 'ParseLinks',
    function ($scope, $state, $modal, InstEmplDesignation, InstEmplDesignationSearch, ParseLinks) {

        $scope.instEmplDesignations = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            InstEmplDesignation.query({page: $scope.page, size:1000}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.instEmplDesignations = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            InstEmplDesignationSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.instEmplDesignations = result;
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
            $scope.instEmplDesignation = {
                code: null,
                name: null,
                description: null,
                type: null,
                status: null,
                id: null
            };
        };
    }]);
