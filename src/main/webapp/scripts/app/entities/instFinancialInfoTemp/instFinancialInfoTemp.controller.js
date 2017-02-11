'use strict';

angular.module('stepApp')
    .controller('InstFinancialInfoTempController', function ($scope, $state, $modal, InstFinancialInfoTemp, InstFinancialInfoTempSearch, ParseLinks) {
      
        $scope.instFinancialInfoTemps = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            InstFinancialInfoTemp.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.instFinancialInfoTemps = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            InstFinancialInfoTempSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.instFinancialInfoTemps = result;
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
            $scope.instFinancialInfoTemp = {
                bankName: null,
                branchName: null,
                accountType: null,
                accountNo: null,
                issueDate: null,
                createDate: null,
                updateDate: null,
                expireDate: null,
                amount: null,
                status: null,
                id: null
            };
        };
    });
