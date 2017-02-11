'use strict';

angular.module('stepApp')
    .controller('PrlBudgetSetupInfoController', function ($scope, $state, PrlBudgetSetupInfo, PrlBudgetSetupInfoSearch, ParseLinks) {

        $scope.prlBudgetSetupInfos = [];
        $scope.predicate = 'codeType';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            PrlBudgetSetupInfo.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.prlBudgetSetupInfos = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            PrlBudgetSetupInfoSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.prlBudgetSetupInfos = result;
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
            $scope.prlBudgetSetupInfo = {
                budgetType: null,
                codeType: null,
                budgetYear: null,
                codeValue: null,
                createDate: null,
                createBy: null,
                updateDate: null,
                updateBy: null,
                id: null
            };
        };
    });
