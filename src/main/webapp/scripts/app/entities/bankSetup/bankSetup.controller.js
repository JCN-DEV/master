'use strict';

angular.module('stepApp')
    .controller('BankSetupController',
    ['$scope', '$state', '$modal', 'BankSetup', 'BankSetupSearch', 'ParseLinks',
    function ($scope, $state, $modal, BankSetup, BankSetupSearch, ParseLinks) {

        $scope.bankSetups = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            BankSetup.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.bankSetups = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            BankSetupSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.bankSetups = result;
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
            $scope.bankSetup = {
                name: null,
                code: null,
                branchName: null,
                id: null
            };
        };
    }]);
