'use strict';

angular.module('stepApp')
    .controller('EmployeeLoanMonthlyDeductionController', function ($scope, EmployeeLoanMonthlyDeduction, EmployeeLoanMonthlyDeductionSearch, ParseLinks) {
        $scope.employeeLoanMonthlyDeductions = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            EmployeeLoanMonthlyDeduction.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.employeeLoanMonthlyDeductions = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            EmployeeLoanMonthlyDeduction.get({id: id}, function(result) {
                $scope.employeeLoanMonthlyDeduction = result;
                $('#deleteEmployeeLoanMonthlyDeductionConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            EmployeeLoanMonthlyDeduction.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteEmployeeLoanMonthlyDeductionConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            EmployeeLoanMonthlyDeductionSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.employeeLoanMonthlyDeductions = result;
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
            $scope.employeeLoanMonthlyDeduction = {
                month: null,
                year: null,
                deductedAmount: null,
                reason: null,
                status: null,
                createDate: null,
                createBy: null,
                updateDate: null,
                updateBy: null,
                id: null
            };
        };
    });
