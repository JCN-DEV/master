'use strict';

angular.module('stepApp')
    .controller('EmployeeLoanCheckRegisterController', function ($scope, EmployeeLoanCheckRegister, EmployeeLoanCheckRegisterSearch, ParseLinks) {
        $scope.employeeLoanCheckRegisters = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            EmployeeLoanCheckRegister.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.employeeLoanCheckRegisters = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            EmployeeLoanCheckRegister.get({id: id}, function(result) {
                $scope.employeeLoanCheckRegister = result;
                $('#deleteEmployeeLoanCheckRegisterConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            EmployeeLoanCheckRegister.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteEmployeeLoanCheckRegisterConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            EmployeeLoanCheckRegisterSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.employeeLoanCheckRegisters = result;
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
            $scope.employeeLoanCheckRegister = {
                applicationType: null,
                numberOfWithdrawal: null,
                checkNumber: null,
                tokenNumber: null,
                status: null,
                createDate: null,
                createBy: null,
                updateDate: null,
                updateBy: null,
                id: null
            };
        };
    });
