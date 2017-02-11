'use strict';

angular.module('stepApp')
    .controller('BankAssignController', function ($scope, $state, $modal, BankAssign, BankAssignSearch, ParseLinks) {
      
        $scope.bankAssigns = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            BankAssign.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.bankAssigns = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            BankAssignSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.bankAssigns = result;
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
            $scope.bankAssign = {
                createdDate: null,
                modifiedDate: null,
                status: null,
                id: null
            };
        };
    });
