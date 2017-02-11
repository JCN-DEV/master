'use strict';

angular.module('stepApp')
    .controller('EmployeeLoanBillRegisterController', function ($scope, EmployeeLoanBillRegister, EmployeeLoanBillRegisterSearch, ParseLinks) {
        $scope.employeeLoanBillRegisters = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            EmployeeLoanBillRegister.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.employeeLoanBillRegisters = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            EmployeeLoanBillRegister.get({id: id}, function(result) {
                $scope.employeeLoanBillRegister = result;
                $('#deleteEmployeeLoanBillRegisterConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            EmployeeLoanBillRegister.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteEmployeeLoanBillRegisterConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            EmployeeLoanBillRegisterSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.employeeLoanBillRegisters = result;
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
            $scope.employeeLoanBillRegister = {
                applicationType: null,
                billNo: null,
                issueDate: null,
                receiverName: null,
                place: null,
                status: null,
                createDate: null,
                createBy: null,
                updateDate: null,
                updateBy: null,
                id: null
            };
        };
    });
