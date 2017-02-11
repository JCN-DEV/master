'use strict';

angular.module('stepApp')
    .controller('PrlHouseRentAllowInfoController', function ($rootScope, $scope, $state, PrlHouseRentAllowInfo, PrlHouseRentAllowInfoSearch, ParseLinks) {

        $scope.prlHouseRentAllowInfos = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 0;
        $scope.stateName = "prlLocalityInfo";
        $scope.loadAll = function()
        {
            if($rootScope.currentStateName == $scope.stateName){
                $scope.page = $rootScope.pageNumber;
            }
            else {
                $rootScope.pageNumber = $scope.page;
                $rootScope.currentStateName = $scope.stateName;
            }
            PrlHouseRentAllowInfo.query({page: $scope.page, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.prlHouseRentAllowInfos = result;
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
            PrlHouseRentAllowInfoSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.prlHouseRentAllowInfos = result;
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
            $scope.prlHouseRentAllowInfo = {
                name: null,
                basicSalaryMin: null,
                basicSalaryMax: null,
                minimumHouseRent: null,
                rentPercentage: null,
                activeStatus: false,
                createDate: null,
                createBy: null,
                updateDate: null,
                updateBy: null,
                id: null
            };
        };
    });
