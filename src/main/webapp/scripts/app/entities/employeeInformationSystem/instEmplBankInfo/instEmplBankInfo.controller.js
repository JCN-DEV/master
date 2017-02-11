'use strict';

angular.module('stepApp')
    .controller('InstEmplBankInfoController',
    ['$scope', '$state', '$modal', 'InstEmplBankInfo', 'InstEmplBankInfoSearch', 'ParseLinks',
    function ($scope, $state, $modal, InstEmplBankInfo, InstEmplBankInfoSearch, ParseLinks) {

        $scope.instEmplBankInfos = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            InstEmplBankInfo.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.instEmplBankInfos = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            InstEmplBankInfoSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.instEmplBankInfos = result;
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
            $scope.instEmplBankInfo = {
                bankName: null,
                bankBranch: null,
                bankAccountNo: null,
                status: null,
                id: null
            };
        };
    }]);
