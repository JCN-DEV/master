'use strict';


angular.module('stepApp')
    .controller('CurriculumMpoListController',
    ['$scope','$location', 'instituteList', 'Principal', 'IisCurriculumsMpoEnlisted', 'ParseLinks',
    function ($scope, $location, instituteList, Principal, IisCurriculumsMpoEnlisted, ParseLinks) {
        $scope.location = $location.path();
        $scope.currentPage = 1;
        $scope.pageSize = 10;
        $scope.currentPageNumber = 'Page Number ' + 1;

        $scope.pageChangeHandler = function(num) {
            console.log('meals page changed to ' + num);
            $scope.currentPageNumber = 'Page Number ' + num;
        };
        $scope.iisCurriculumInfos = [];
        IisCurriculumsMpoEnlisted.query({page: 0, size: 2000}, function(result){
            $scope.iisCurriculumInfos = result;
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
