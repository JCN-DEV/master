'use strict';

angular.module('stepApp')
    .controller('BankBranchController', function ($scope, $state, $modal, BankBranch, BankBranchSearch, ParseLinks) {
      
        $scope.bankBranchs = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            BankBranch.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.bankBranchs = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            BankBranchSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.bankBranchs = result;
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
            $scope.bankBranch = {
                brName: null,
                address: null,
                status: null,
                id: null
            };
        };
    });
