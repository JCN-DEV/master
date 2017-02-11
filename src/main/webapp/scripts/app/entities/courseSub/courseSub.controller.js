'use strict';

angular.module('stepApp')
    .controller('CourseSubController',
    ['$scope', '$state', '$modal', 'CourseSub', 'CourseSubSearch', 'ParseLinks',
    function ($scope, $state, $modal, CourseSub, CourseSubSearch, ParseLinks) {

        $scope.courseSubs = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            CourseSub.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.courseSubs = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            CourseSubSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.courseSubs = result;
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
            $scope.courseSub = {
                code: null,
                name: null,
                description: null,
                status: null,
                id: null
            };
        };
    }]);
