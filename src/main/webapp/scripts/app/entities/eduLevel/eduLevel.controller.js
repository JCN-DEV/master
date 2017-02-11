'use strict';

angular.module('stepApp')
    .controller('EduLevelController',
    ['$scope', '$state', '$modal', 'EduLevel', 'EduLevelSearch', 'ParseLinks',
     function ($scope, $state, $modal, EduLevel, EduLevelSearch, ParseLinks) {

        $scope.eduLevels = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            EduLevel.query({page: $scope.page, size: 5000}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.eduLevels = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            EduLevelSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.eduLevels = result;
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
            $scope.eduLevel = {
                name: null,
                description: null,
                status: null,
                id: null
            };
        };
    }]);
