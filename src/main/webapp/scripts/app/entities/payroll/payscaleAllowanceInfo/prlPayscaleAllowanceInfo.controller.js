'use strict';

angular.module('stepApp')
    .controller('PrlPayscaleAllowanceInfoController', function ($scope, $state, PrlPayscaleAllowanceInfo, PrlPayscaleAllowanceInfoSearch, ParseLinks) {

        $scope.prlPayscaleAllowanceInfos = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.stateName = "prlPayscaleInfo";
        $scope.page = 1;
        $scope.loadAll = function() {
            PrlPayscaleAllowanceInfo.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.prlPayscaleAllowanceInfos = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            PrlPayscaleAllowanceInfoSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.prlPayscaleAllowanceInfos = result;
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
            $scope.prlPayscaleAllowanceInfo = {
                fixedBasic: null,
                basicMinimum: null,
                basicMaximum: null,
                activeStatus: false,
                createDate: null,
                createBy: null,
                updateDate: null,
                updateBy: null,
                id: null
            };
        };
    });
