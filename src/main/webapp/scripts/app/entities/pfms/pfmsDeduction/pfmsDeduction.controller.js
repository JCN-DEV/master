'use strict';

angular.module('stepApp')
    .controller('PfmsDeductionController', function ($scope, $rootScope, PfmsDeduction, PfmsDeductionSearch, ParseLinks) {
        $scope.pfmsDeductions = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            PfmsDeduction.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.pfmsDeductions = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            PfmsDeduction.get({id: id}, function(result) {
                $scope.pfmsDeduction = result;
                $('#deletePfmsDeductionConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            PfmsDeduction.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deletePfmsDeductionConfirmation').modal('hide');
                    $scope.clear();
                    $rootScope.setErrorMessage('stepApp.pfmsDeduction.deleted');
                });
        };

        $scope.search = function () {
            PfmsDeductionSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.pfmsDeductions = result;
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
            $scope.pfmsDeduction = {
                accountNo: null,
                deductionAmount: null,
                deductionDate: null,
                activeStatus: false,
                createDate: null,
                createBy: null,
                updateDate: null,
                updateBy: null,
                id: null
            };
        };
    });
