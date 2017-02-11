'use strict';

angular.module('stepApp')
    .controller('EmployeeLoanRulesSetupController', function ($scope, EmployeeLoanRulesSetup, EmployeeLoanRulesSetupSearch, ParseLinks) {
        $scope.employeeLoanRulesSetups = [];
        $scope.page = 0;
        $scope.currentPage = 1;
        $scope.pageSize = 10;
        $scope.loadAll = function() {
            EmployeeLoanRulesSetup.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.employeeLoanRulesSetups = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            EmployeeLoanRulesSetup.get({id: id}, function(result) {
                $scope.employeeLoanRulesSetup = result;
                $('#deleteEmployeeLoanRulesSetupConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            EmployeeLoanRulesSetup.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteEmployeeLoanRulesSetupConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            EmployeeLoanRulesSetupSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.employeeLoanRulesSetups = result;
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
            $scope.employeeLoanRulesSetup = {
                loanName: null,
                loanRulesDescription: null,
                maximumWithdrawal: null,
                minimumAmountBasic: null,
                interest: null,
                status: null,
                createDate: null,
                createBy: null,
                updateDate: null,
                updateBy: null,
                id: null
            };
        };
    });
