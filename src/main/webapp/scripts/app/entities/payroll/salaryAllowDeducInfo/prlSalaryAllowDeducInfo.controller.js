'use strict';

angular.module('stepApp')
    .controller('PrlSalaryAllowDeducInfoController', function ($scope, $state, PrlSalaryAllowDeducInfo, PrlSalaryAllowDeducInfoSearch, ParseLinks) {

        $scope.prlSalaryAllowDeducInfos = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            PrlSalaryAllowDeducInfo.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.prlSalaryAllowDeducInfos = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            PrlSalaryAllowDeducInfoSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.prlSalaryAllowDeducInfos = result;
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
            $scope.prlSalaryAllowDeducInfo = {
                allowDeducType: null,
                allowDeducValue: null,
                activeStatus: false,
                createDate: null,
                createBy: null,
                updateDate: null,
                updateBy: null,
                id: null
            };
        };
    });
