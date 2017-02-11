'use strict';

angular.module('stepApp')
    .controller('CourseTechController',
    ['$scope', '$state', '$modal', 'CourseTech', 'CourseTechSearch', 'ParseLinks',
    function ($scope, $state, $modal, CourseTech, CourseTechSearch, ParseLinks) {

        $scope.courseTechs = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            CourseTech.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.courseTechs = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            CourseTechSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.courseTechs = result;
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
            $scope.courseTech = {
                code: null,
                name: null,
                description: null,
                status: null,
                id: null
            };
        };
    }]);
