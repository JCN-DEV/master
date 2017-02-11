'use strict';

angular.module('stepApp')
    .controller('PrlGeneratedSalaryInfoController', function ($scope, $state, PrlGeneratedSalaryInfo, PrlGeneratedSalaryInfoSearch, ParseLinks) {

        $scope.prlGeneratedSalaryInfos = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            PrlGeneratedSalaryInfo.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.prlGeneratedSalaryInfos = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            PrlGeneratedSalaryInfoSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.prlGeneratedSalaryInfos = result;
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
            $scope.prlGeneratedSalaryInfo = {
                yearName: null,
                monthName: null,
                salaryType: null,
                processDate: null,
                disburseStatus: 'N',
                createDate: null,
                createBy: null,
                updateDate: null,
                updateBy: null,
                id: null
            };
        };
    });
