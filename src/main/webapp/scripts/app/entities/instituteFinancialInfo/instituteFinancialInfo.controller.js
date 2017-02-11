'use strict';

angular.module('stepApp')
    .controller('InstituteFinancialInfoController',
    ['$scope','$state','$modal','InstituteFinancialInfo','InstituteFinancialInfoSearch','ParseLinks',
    function ($scope, $state, $modal, InstituteFinancialInfo, InstituteFinancialInfoSearch, ParseLinks) {

        $scope.instituteFinancialInfos = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            InstituteFinancialInfo.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.instituteFinancialInfos = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            InstituteFinancialInfoSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.instituteFinancialInfos = result;
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
            $scope.instituteFinancialInfo = {
                bankName: null,
                branchName: null,
                accountType: null,
                accountNo: null,
                issueDate: null,
                expireDate: null,
                amount: null,
                status: null,
                id: null
            };
        };
    }]);
