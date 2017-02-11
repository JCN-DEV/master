'use strict';

angular.module('stepApp')
    .controller('InstLevelController',
    ['$scope', '$state', '$modal', 'InstLevel', 'InstLevelSearch', 'ParseLinks',
    function ($scope, $state, $modal, InstLevel, InstLevelSearch, ParseLinks) {

        $scope.instLevels = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            InstLevel.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.instLevels = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            InstLevelSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.instLevels = result;
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
            $scope.instLevel = {
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
