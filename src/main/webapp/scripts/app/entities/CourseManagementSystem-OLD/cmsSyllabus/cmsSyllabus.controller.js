'use strict';

angular.module('stepApp')
    .controller('CmsSyllabusController',
    ['$scope', '$state', '$modal', 'CmsSyllabus', 'CmsSyllabusSearch', 'ParseLinks',
    function ($scope, $state, $modal, CmsSyllabus, CmsSyllabusSearch, ParseLinks) {

        $scope.cmsSyllabuss = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            CmsSyllabus.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.cmsSyllabuss = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            CmsSyllabusSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.cmsSyllabuss = result;
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
            $scope.cmsSyllabus = {
                version: null,
                name: null,
                description: null,
                status: null,
                id: null
            };
        };
    }]);
