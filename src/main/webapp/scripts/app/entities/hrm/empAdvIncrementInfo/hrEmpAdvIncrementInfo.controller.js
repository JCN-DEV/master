'use strict';

angular.module('stepApp')
    .controller('HrEmpAdvIncrementInfoController',
    ['$rootScope','$scope', '$state', 'DataUtils', 'HrEmpAdvIncrementInfo', 'HrEmpAdvIncrementInfoSearch', 'ParseLinks',
    function ($rootScope,$scope, $state, DataUtils, HrEmpAdvIncrementInfo, HrEmpAdvIncrementInfoSearch, ParseLinks) {

        $scope.hrEmpAdvIncrementInfos = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 0;
        $scope.stateName = "hrEmpAdvIncrementInfo";
        $scope.loadAll = function()
        {
            if($rootScope.currentStateName == $scope.stateName){
                $scope.page = $rootScope.pageNumber;
            }
            else {
                $rootScope.pageNumber = $scope.page;
                $rootScope.currentStateName = $scope.stateName;
            }
            HrEmpAdvIncrementInfo.query({page: $scope.page, size: 5000, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.hrEmpAdvIncrementInfos = result;
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
            HrEmpAdvIncrementInfoSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.hrEmpAdvIncrementInfos = result;
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
            $scope.hrEmpAdvIncrementInfo = {
                postName: null,
                purpose: null,
                incrementAmount: null,
                orderDate: null,
                orderNumber: null,
                remarks: null,
                goDate: null,
                goDoc: null,
                goDocContentType: null,
                goDocName: null,
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
