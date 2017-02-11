'use strict';

angular.module('stepApp')
    .controller('AlmEmpLeaveBalanceController',
    ['$scope', 'AlmEmpLeaveBalance', 'AlmEmpLeaveBalanceSearch', 'ParseLinks',
    function ($scope, AlmEmpLeaveBalance, AlmEmpLeaveBalanceSearch, ParseLinks) {
        $scope.almEmpLeaveBalances = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            AlmEmpLeaveBalance.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.almEmpLeaveBalances = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            AlmEmpLeaveBalance.get({id: id}, function(result) {
                $scope.almEmpLeaveBalance = result;
                $('#deleteAlmEmpLeaveBalanceConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            AlmEmpLeaveBalance.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteAlmEmpLeaveBalanceConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            AlmEmpLeaveBalanceSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.almEmpLeaveBalances = result;
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
            $scope.almEmpLeaveBalance = {
                yearOpenDate: null,
                year: null,
                leaveEarned: null,
                leaveTaken: null,
                leaveForwarded: null,
                attendanceLeave: null,
                leaveOnApply: null,
                leaveBalance: null,
                activeStatus: null,
                createDate: null,
                createBy: null,
                updateDate: null,
                updateBy: null,
                id: null
            };
        };
    }]);
