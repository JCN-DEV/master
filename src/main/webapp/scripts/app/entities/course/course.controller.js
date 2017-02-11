'use strict';

angular.module('stepApp')
    .controller('CourseController',
    ['$scope', '$state', '$modal', 'Course', 'CourseSearch', 'ParseLinks',
    function ($scope, $state, $modal, Course, CourseSearch, ParseLinks) {

        $scope.courses = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Course.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.courses = result;
                $scope.total = headers('x-total-count');
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };

        $scope.loadAll();

        $scope.search = function () {
            CourseSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.courses = result;
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
            $scope.course = {
                course: null,
                id: null
            };
        };

        // bulk operations start
        $scope.areAllCoursesSelected = false;

        $scope.updateCoursesSelection = function (courseArray, selectionValue) {
            for (var i = 0; i < courseArray.length; i++)
            {
            courseArray[i].isSelected = selectionValue;
            }
        };


        $scope.import = function (){
            for (var i = 0; i < $scope.courses.length; i++){
                var course = $scope.courses[i];
                if(course.isSelected){
                    //Course.update(course);
                    //TODO: handle bulk export
                }
            }
        };

        $scope.export = function (){
            for (var i = 0; i < $scope.courses.length; i++){
                var course = $scope.courses[i];
                if(course.isSelected){
                    //Course.update(course);
                    //TODO: handle bulk export
                }
            }
        };

        $scope.deleteSelected = function (){
            for (var i = 0; i < $scope.courses.length; i++){
                var course = $scope.courses[i];
                if(course.isSelected){
                    Course.delete(course);
                }
            }
        };

        $scope.sync = function (){
            for (var i = 0; i < $scope.courses.length; i++){
                var course = $scope.courses[i];
                if(course.isSelected){
                    Course.update(course);
                }
            }
        };

        $scope.order = function (predicate, reverse) {
            $scope.predicate = predicate;
            $scope.reverse = reverse;
            Course.query({page: $scope.page, size: 20}, function (result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.courses = result;
                $scope.total = headers('x-total-count');
            });
        };
        // bulk operations end

    }]);
