'use strict';

angular.module('stepApp')
    .controller('HrEmpAcrInfoController',
     ['$rootScope','$scope', '$state', 'HrEmpAcrInfo', 'HrEmpAcrInfoSearch', 'ParseLinks',
     function ($rootScope,$scope, $state, HrEmpAcrInfo, HrEmpAcrInfoSearch, ParseLinks) {

        $scope.hrEmpAcrInfos = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 0;
        $scope.stateName = "hrEmpAcrInfo";
        $scope.loadAll = function()
        {
            if($rootScope.currentStateName == $scope.stateName){
                $scope.page = $rootScope.pageNumber;
            }
            else {
                $rootScope.pageNumber = $scope.page;
                $rootScope.currentStateName = $scope.stateName;
            }
            HrEmpAcrInfo.query({page: $scope.page, size: 5000, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.hrEmpAcrInfos = result;
            });
        };
        $scope.loadPage = function(page)
        {
            $rootScope.currentStateName = $scope.stateName;
            $rootScope.pageNumber = page;
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            HrEmpAcrInfoSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.hrEmpAcrInfos = result;
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
            $scope.hrEmpAcrInfo = {
                acrYear: null,
                totalMarks: null,
                overallEvaluation: null,
                promotionStatus: null,
                acrDate: null,
                logId: null,
                logStatus: null,
                activeStatus: true,
                createDate: null,
                createBy: null,
                updateDate: null,
                updateBy: null,
                id: null
            };
        };
    }]);
