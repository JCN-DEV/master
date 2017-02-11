'use strict';

angular.module('stepApp')
    .controller('EmployeeLoanTypeSetupController', function ($scope, EmployeeLoanTypeSetup, EmployeeLoanTypeSetupSearch, ParseLinks) {
        $scope.employeeLoanTypeSetups = [];
        $scope.page = 0;
        $scope.currentPage = 1;
        $scope.pageSize = 10;
        $scope.loadAll = function() {
            EmployeeLoanTypeSetup.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.employeeLoanTypeSetups = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.employeeLoanTypeSetup = {
                loanTypeCode: null,
                loanTypeName: null,
                status: null,
                createDate: null,
                createBy: null,
                updateDate: null,
                updateBy: null,
                id: null
            };
        };
    });
