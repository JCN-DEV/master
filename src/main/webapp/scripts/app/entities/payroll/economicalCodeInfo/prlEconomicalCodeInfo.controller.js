'use strict';

angular.module('stepApp')
    .controller('PrlEconomicalCodeInfoController', function ($scope, $state, PrlEconomicalCodeInfo, PrlEconomicalCodeInfoSearch, ParseLinks) {

        $scope.prlEconomicalCodeInfos = [];
        $scope.predicate = 'codeType';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            PrlEconomicalCodeInfo.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.prlEconomicalCodeInfos = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            PrlEconomicalCodeInfoSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.prlEconomicalCodeInfos = result;
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
            $scope.prlEconomicalCodeInfo = {
                codeType: null,
                codeName: null,
                createDate: null,
                createBy: null,
                updateDate: null,
                updateBy: null,
                id: null
            };
        };
    });
