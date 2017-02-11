'use strict';

angular.module('stepApp')
    .controller('HrGradeSetupController',
     ['$scope', '$state', 'HrGradeSetup', 'HrGradeSetupSearch', 'ParseLinks',
     function ($scope, $state, HrGradeSetup, HrGradeSetupSearch, ParseLinks) {

        $scope.hrGradeSetups = [];
        $scope.predicate = 'gradeCode';
        $scope.reverse = true;
        $scope.page = 0;
        $scope.loadAll = function()
        {
            console.log("pg: "+$scope.page+", pred:"+$scope.predicate+", rev: "+$scope.reverse);
            HrGradeSetup.query({page: $scope.page, size: 5000, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.hrGradeSetups = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            HrGradeSetupSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.hrGradeSetups = result;
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
            $scope.hrGradeSetup = {
                gradeCode: null,
                gradeDetail: null,
                activeStatus: true,
                createDate: null,
                createBy: null,
                updateDate: null,
                updateBy: null,
                id: null
            };
        };
    }]);
