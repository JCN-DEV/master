'use strict';

angular.module('stepApp')
    .controller('HrEmpAddressInfoController',
    ['$rootScope', '$scope', '$state', 'HrEmpAddressInfo', 'HrEmpAddressInfoSearch', 'ParseLinks',
    function ($rootScope, $scope, $state, HrEmpAddressInfo, HrEmpAddressInfoSearch, ParseLinks) {

        $scope.hrEmpAddressInfos = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 0;
        $scope.stateName = "hrEmpAddressInfo";
        $scope.loadAll = function()
        {
            if($rootScope.currentStateName == $scope.stateName){
                $scope.page = $rootScope.pageNumber;
            }
            else {
                $rootScope.pageNumber = $scope.page;
                $rootScope.currentStateName = $scope.stateName;
            }
            //console.log("pg: "+$scope.page+", pred:"+$scope.predicate+", rootPage: "+$rootScope.pageNumber+", rootSc: "+$rootScope.currentStateName+", lcst:"+$scope.stateName);

            HrEmpAddressInfo.query({page: $scope.page, size: 5000, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.hrEmpAddressInfos = result;
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
            HrEmpAddressInfoSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.hrEmpAddressInfos = result;
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
            $scope.hrEmpAddressInfo = {
                addressType: null,
                houseNumber: null,
                houseNumberBn: null,
                roadNumber: null,
                roadNumberBn: null,
                villageName: null,
                villageNameBn: null,
                postOffice: null,
                postOfficeBn: null,
                policeStation: null,
                policeStationBn: null,
                district: null,
                districtBn: null,
                contactNumber: null,
                activeStatus: true,
                createDate: null,
                createBy: null,
                updateDate: null,
                updateBy: null,
                id: null
            };
        };
    }]);
