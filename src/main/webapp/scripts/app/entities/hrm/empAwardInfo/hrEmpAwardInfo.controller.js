'use strict';

angular.module('stepApp')
    .controller('HrEmpAwardInfoController',
     ['$rootScope','$scope', '$state', 'DataUtils', 'HrEmpAwardInfo', 'HrEmpAwardInfoSearch', 'ParseLinks',
     function ($rootScope,$scope, $state, DataUtils, HrEmpAwardInfo, HrEmpAwardInfoSearch, ParseLinks) {

        $scope.hrEmpAwardInfos = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 0;
        $scope.stateName = "hrEmpAwardInfo";
        $scope.loadAll = function()
        {
            if($rootScope.currentStateName == $scope.stateName){
                $scope.page = $rootScope.pageNumber;
            }
            else {
                $rootScope.pageNumber = $scope.page;
                $rootScope.currentStateName = $scope.stateName;
            }
            HrEmpAwardInfo.query({page: $scope.page, size: 5000, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.hrEmpAwardInfos = result;
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
            HrEmpAwardInfoSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.hrEmpAwardInfos = result;
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
            $scope.hrEmpAwardInfo = {
                awardName: null,
                awardArea: null,
                awardDate: null,
                remarks: null,
                goOrderDoc: null,
                goOrderDocContentType: null,
                goOrderDocName: null,
                certDoc: null,
                certDocContentType: null,
                certDocName: null,
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

        $scope.abbreviate = DataUtils.abbreviate;

        $scope.byteSize = DataUtils.byteSize;
    }]);
