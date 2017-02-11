'use strict';

angular.module('stepApp')
    .controller('HrEmpPreGovtJobInfoController',
     ['$rootScope', '$scope', '$state', 'HrEmpPreGovtJobInfo', 'HrEmpPreGovtJobInfoSearch', 'ParseLinks',
     function ($rootScope, $scope, $state, HrEmpPreGovtJobInfo, HrEmpPreGovtJobInfoSearch, ParseLinks) {

        $scope.hrEmpPreGovtJobInfos = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 0;
        $scope.stateName = "hrEmpPreGovtJobInfo";
        $scope.loadAll = function()
        {
            if($rootScope.currentStateName == $scope.stateName){
                $scope.page = $rootScope.pageNumber;
            }
            else {
                $rootScope.pageNumber = $scope.page;
                $rootScope.currentStateName = $scope.stateName;
            }
            HrEmpPreGovtJobInfo.query({page: $scope.page, size: 5000, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.hrEmpPreGovtJobInfos = result;
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
            HrEmpPreGovtJobInfoSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.hrEmpPreGovtJobInfos = result;
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
            $scope.hrEmpPreGovtJobInfo = {
                organizationName: null,
                postName: null,
                address: null,
                fromDate: null,
                toDate: null,
                salary: null,
                comments: null,
                logId:null,
                logStatus:null,
                logComments:null,
                activeStatus: true,
                createDate: null,
                createBy: null,
                updateDate: null,
                updateBy: null,
                id: null
            };
        };
    }]);
