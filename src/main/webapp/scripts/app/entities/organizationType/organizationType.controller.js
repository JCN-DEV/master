'use strict';

angular.module('stepApp')
    .controller('OrganizationTypeController',

    ['$scope','$state','$modal','OrganizationType','OrganizationTypeSearch','ParseLinks',
    function ($scope, $state, $modal, OrganizationType, OrganizationTypeSearch, ParseLinks) {

        $scope.organizationTypes = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            OrganizationType.query({page: $scope.page, size: 5000}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.organizationTypes = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            OrganizationTypeSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.organizationTypes = result;
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
            $scope.organizationType = {
                name: null,
                description: null,
                status: null,
                id: null
            };
        };
    }]);
