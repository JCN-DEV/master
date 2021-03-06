'use strict';

angular.module('stepApp')
    .controller('PrlSalaryStructureInfoController', function ($rootScope,$scope, $state, PrlSalaryStructureInfo, PrlSalaryStructureInfoSearch, ParseLinks) {

        $scope.prlSalaryStructureInfos = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.stateName = "prlSalaryStructureInfo";
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
            PrlSalaryStructureInfo.query({page: $scope.page, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.prlSalaryStructureInfos = result;
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
            PrlSalaryStructureInfoSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.prlSalaryStructureInfos = result;
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
            $scope.prlSalaryStructureInfo = {
                effectiveDate: null,
                basicAmount: null,
                activeStatus: false,
                createDate: null,
                createBy: null,
                updateDate: null,
                updateBy: null,
                id: null
            };
        };
    });
