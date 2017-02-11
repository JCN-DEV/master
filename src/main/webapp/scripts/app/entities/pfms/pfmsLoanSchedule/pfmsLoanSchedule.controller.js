'use strict';

angular.module('stepApp')
    .controller('PfmsLoanScheduleController', function ($scope, $rootScope, PfmsLoanSchedule, PfmsLoanScheduleSearch, ParseLinks) {
        $scope.pfmsLoanSchedules = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            PfmsLoanSchedule.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.pfmsLoanSchedules = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            PfmsLoanSchedule.get({id: id}, function(result) {
                $scope.pfmsLoanSchedule = result;
                $('#deletePfmsLoanScheduleConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            PfmsLoanSchedule.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deletePfmsLoanScheduleConfirmation').modal('hide');
                    $scope.clear();
                    $rootScope.setErrorMessage('stepApp.pfmsLoanSchedule.deleted');
                });
        };

        $scope.search = function () {
            PfmsLoanScheduleSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.pfmsLoanSchedules = result;
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
            $scope.pfmsLoanSchedule = {
                loanYear: null,
                loanMonth: null,
                deductedAmount: null,
                installmentNo: null,
                totInstallNo: null,
                totLoanAmount: null,
                isRepay: false,
                reason: null,
                activeStatus: false,
                createDate: null,
                createBy: null,
                updateDate: null,
                updateBy: null,
                id: null
            };
        };
    });
