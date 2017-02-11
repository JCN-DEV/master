'use strict';

angular.module('stepApp')
    .controller('CourseSubjectController',
    ['$scope', '$state', '$modal', 'CourseSubject', 'CourseSubjectSearch', 'ParseLinks',
    function ($scope, $state, $modal, CourseSubject, CourseSubjectSearch, ParseLinks) {

        $scope.courseSubjects = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            CourseSubject.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.courseSubjects = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            CourseSubjectSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.courseSubjects = result;
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
            $scope.courseSubject = {
                code: null,
                name: null,
                description: null,
                status: null,
                id: null
            };
        };
    }]);
