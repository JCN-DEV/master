'use strict';

angular.module('stepApp')
    .controller('PrlPayscaleBasicInfoController', function ($scope, $state, PrlPayscaleBasicInfo, PrlPayscaleBasicInfoSearch, ParseLinks) {

        $scope.prlPayscaleBasicInfos = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.stateName = "prlPayscaleInfo";
        $scope.page = 0;
        $scope.loadAll = function() {
            PrlPayscaleBasicInfo.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.prlPayscaleBasicInfos = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            PrlPayscaleBasicInfoSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.prlPayscaleBasicInfos = result;
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
            $scope.prlPayscaleBasicInfo = {
                serialNumber: null,
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
