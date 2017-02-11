'use strict';

angular.module('stepApp')
    .controller('PrlAllowDeductInfoController', function ($rootScope, $scope, $state, PrlAllowDeductInfo, PrlAllowDeductInfoSearch, ParseLinks) {

        $scope.prlAllowDeductInfos = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.stateName = "prlAllowDeductInfo";
        $scope.page = 0;
        $scope.loadAll = function()
        {
            if($rootScope.currentStateName == $scope.stateName){
                $scope.page = $rootScope.pageNumber;
            }
            else {
                $rootScope.pageNumber = $scope.page;
                $rootScope.currentStateName = $scope.stateName;
            }
            PrlAllowDeductInfo.query({page: $scope.page, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.prlAllowDeductInfos = result;
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
            PrlAllowDeductInfoSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.prlAllowDeductInfos = result;
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

        $scope.allowDedFilter = function (alddInfo)
        {
            if (alddInfo.allowDeducType === 'Allowance' || alddInfo.allowDeducType === 'Deduction')
            {
                return alddInfo;
            }
        };

        $scope.clear = function () {
            $scope.prlAllowDeductInfo = {
                name: null,
                allowDeducType: null,
                description: null,
                activeStatus: false,
                createDate: null,
                createBy: null,
                updateDate: null,
                updateBy: null,
                id: null
            };
        };
    });
