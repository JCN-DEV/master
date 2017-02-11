'use strict';

angular.module('stepApp')
    .controller('HrEmpBankAccountInfoController',
    ['$rootScope','$scope', '$state', 'HrEmpBankAccountInfo', 'HrEmpBankAccountInfoSearch', 'ParseLinks',
    function ($rootScope,$scope, $state, HrEmpBankAccountInfo, HrEmpBankAccountInfoSearch, ParseLinks) {

        $scope.hrEmpBankAccountInfos = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.stateName = "hrEmpBankAccountInfo";
        $scope.loadAll = function()
        {
            if($rootScope.currentStateName == $scope.stateName){
                $scope.page = $rootScope.pageNumber;
            }
            else {
                $rootScope.pageNumber = $scope.page;
                $rootScope.currentStateName = $scope.stateName;
            }
            HrEmpBankAccountInfo.query({page: $scope.page - 1, size: 5000, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.hrEmpBankAccountInfos = result;
            });
        };
        $scope.loadPage = function(page)
        {
            $rootScope.currentStateName = $scope.stateName;
            $rootScope.pageNumber = page;
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            HrEmpBankAccountInfoSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.hrEmpBankAccountInfos = result;
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
            $scope.hrEmpBankAccountInfo = {
                accountName: null,
                accountNumber: null,
                branchName: null,
                description: null,
                salaryAccount: false,
                activeStatus: false,
                logId: null,
                logStatus: null,
                logComments: null,
                createDate: null,
                createBy: null,
                updateDate: null,
                updateBy: null,
                id: null
            };
        };
    }]);
