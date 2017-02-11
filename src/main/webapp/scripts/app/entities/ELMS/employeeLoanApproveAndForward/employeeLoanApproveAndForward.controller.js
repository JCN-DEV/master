'use strict';

angular.module('stepApp')
    .controller('EmployeeLoanApproveAndForwardController', function ($scope, EmployeeLoanApproveAndForward, EmployeeLoanApproveAndForwardSearch, ParseLinks) {
        $scope.employeeLoanApproveAndForwards = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            EmployeeLoanApproveAndForward.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.employeeLoanApproveAndForwards = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            EmployeeLoanApproveAndForward.get({id: id}, function(result) {
                $scope.employeeLoanApproveAndForward = result;
                $('#deleteEmployeeLoanApproveAndForwardConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            EmployeeLoanApproveAndForward.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteEmployeeLoanApproveAndForwardConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            EmployeeLoanApproveAndForwardSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.employeeLoanApproveAndForwards = result;
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
            $scope.employeeLoanApproveAndForward = {
                comments: null,
                approveStatus: null,
                createDate: null,
                createBy: null,
                updateDate: null,
                updateBy: null,
                id: null
            };
        };
    });
