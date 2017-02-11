'use strict';

angular.module('stepApp')
    .controller('OrganizationCategoryController',
    ['$scope', '$state', '$modal', 'OrganizationCategory', 'OrganizationCategorySearch', 'ParseLinks',
    function ($scope, $state, $modal, OrganizationCategory, OrganizationCategorySearch, ParseLinks) {

        $scope.organizationCategorys = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            OrganizationCategory.query({page: $scope.page, size: 5000}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.organizationCategorys = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            OrganizationCategorySearch.query({query: $scope.searchQuery}, function(result) {
                $scope.organizationCategorys = result;
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
            $scope.organizationCategory = {
                name: null,
                description: null,
                status: null,
                id: null
            };
        };
    }]);
