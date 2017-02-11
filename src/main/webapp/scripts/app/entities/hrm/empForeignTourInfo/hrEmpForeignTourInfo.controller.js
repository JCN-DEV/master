'use strict';

angular.module('stepApp')
    .controller('HrEmpForeignTourInfoController',
     ['$rootScope','$scope', '$state', 'DataUtils', 'HrEmpForeignTourInfo', 'HrEmpForeignTourInfoSearch', 'ParseLinks',
     function ($rootScope,$scope, $state, DataUtils, HrEmpForeignTourInfo, HrEmpForeignTourInfoSearch, ParseLinks) {

        $scope.hrEmpForeignTourInfos = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 0;
        $scope.stateName = "hrEmpForeignTourInfo";
        $scope.loadAll = function()
        {
            if($rootScope.currentStateName == $scope.stateName){
                $scope.page = $rootScope.pageNumber;
            }
            else {
                $rootScope.pageNumber = $scope.page;
                $rootScope.currentStateName = $scope.stateName;
            }
            HrEmpForeignTourInfo.query({page: $scope.page, size: 5000, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.hrEmpForeignTourInfos = result;
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
            HrEmpForeignTourInfoSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.hrEmpForeignTourInfos = result;
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
            $scope.hrEmpForeignTourInfo = {
                purpose: null,
                fromDate: null,
                toDate: null,
                countryName: null,
                officeOrderNumber: null,
                fundSource: null,
                goDate: null,
                goDoc: null,
                goDocContentType: null,
                goDocName: null,
                goNumber: null,
                officeOrderDate: null,
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
