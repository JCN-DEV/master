'use strict';

angular.module('stepApp')
    .controller('JpSkillController',
     ['$scope', '$state', '$modal', 'JpSkill', 'JpSkillSearch', 'ParseLinks',
     function ($scope, $state, $modal, JpSkill, JpSkillSearch, ParseLinks) {

        $scope.jpSkills = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            JpSkill.query({page: $scope.page, size: 5000}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.jpSkills = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            JpSkillSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.jpSkills = result;
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
            $scope.jpSkill = {
                name: null,
                description: null,
                status: null,
                id: null
            };
        };
    }]);
