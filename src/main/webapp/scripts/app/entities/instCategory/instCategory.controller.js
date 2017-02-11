'use strict';

angular.module('stepApp')
    .controller('InstCategoryController',
    ['$scope','$state','$modal','InstCategory','InstCategorySearch','ParseLinks',
    function ($scope, $state, $modal, InstCategory, InstCategorySearch, ParseLinks) {

        $scope.instCategorys = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            InstCategory.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.instCategorys = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            InstCategorySearch.query({query: $scope.searchQuery}, function(result) {
                $scope.instCategorys = result;
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
            $scope.instCategory = {
                code: null,
                name: null,
                description: null,
                pStatus: null,
                createdDate: null,
                updatedDate: null,
                createdBy: null,
                updatedBy: null,
                status: null,
                id: null
            };
        };
    }]);
