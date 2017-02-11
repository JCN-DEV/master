'use strict';

angular.module('stepApp')
    .controller('JpSkillLevelController',
     ['$scope', '$state', '$modal', 'JpSkillLevel', 'JpSkillLevelSearch', 'ParseLinks',
     function ($scope, $state, $modal, JpSkillLevel, JpSkillLevelSearch, ParseLinks) {

        $scope.jpSkillLevels = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            JpSkillLevel.query({page: $scope.page, size: 5000}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.jpSkillLevels = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            JpSkillLevelSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.jpSkillLevels = result;
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
            $scope.jpSkillLevel = {
                name: null,
                description: null,
                status: null,
                id: null
            };
        };
    }]);
