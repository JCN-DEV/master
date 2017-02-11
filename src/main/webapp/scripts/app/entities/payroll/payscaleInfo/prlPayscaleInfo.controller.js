'use strict';

angular.module('stepApp')
    .controller('PrlPayscaleInfoController', function ($rootScope, $scope, $state, PrlPayscaleInfo, PrlPayscaleInfoSearch, ParseLinks) {

        $scope.prlPayscaleInfos = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.stateName = "prlPayscaleInfo";
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
            PrlPayscaleInfo.query({page: $scope.page, size: 5000, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.prlPayscaleInfos = result;
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
            PrlPayscaleInfoSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.prlPayscaleInfos = result;
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
            $scope.prlPayscaleInfo = {
                name: null,
                maxBasicElegYear: null,
                activeStatus: true,
                createDate: null,
                createBy: null,
                updateDate: null,
                updateBy: null,
                id: null
            };
        };
    });
