'use strict';


angular.module('stepApp')
    .controller('CourseMpoListController',
    ['$scope','$location', 'instituteList', 'Principal', 'MpoEnlistedIISCourses', 'ParseLinks',
    function ($scope, $location, instituteList, Principal, MpoEnlistedIISCourses, ParseLinks) {
        $scope.location = $location.path();
        $scope.currentPage = 1;
        $scope.pageSize = 10;
        $scope.currentPageNumber = 'Page Number ' + 1;

        $scope.pageChangeHandler = function(num) {
            console.log('meals page changed to ' + num);
            $scope.currentPageNumber = 'Page Number ' + num;
        };
        $scope.iisCourseInfos = [];
        MpoEnlistedIISCourses.query({page: 0, size: 2000}, function(result){
            $scope.iisCourseInfos = result;
        });

/*

        $scope.instGenInfos = [];
        $scope.page = 0;
        $scope.showapprove = false;
        $scope.loadAll = function() {
            InstGenInfoByStatus.query({status:instituteList ,page: $scope.page, size: 1000}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.instGenInfos = result;
                if($scope.instGenInfos[0].status === 1){
                    $scope.showapprove = true;
                }
                $scope.total = headers('x-total-count');
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();*/

    }]);
