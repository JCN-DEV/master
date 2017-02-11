'use strict';

angular.module('stepApp')
    .controller('PfmsEmpMonthlyAdjController', function ($scope, PfmsEmpMonthlyAdj, PfmsEmpMonthlyAdjSearch, ParseLinks) {
        $scope.pfmsEmpMonthlyAdjs = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            PfmsEmpMonthlyAdj.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.pfmsEmpMonthlyAdjs = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            PfmsEmpMonthlyAdj.get({id: id}, function(result) {
                $scope.pfmsEmpMonthlyAdj = result;
                $('#deletePfmsEmpMonthlyAdjConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            PfmsEmpMonthlyAdj.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deletePfmsEmpMonthlyAdjConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            PfmsEmpMonthlyAdjSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.pfmsEmpMonthlyAdjs = result;
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
            $scope.pfmsEmpMonthlyAdj = {
                adjYear: null,
                adjMonth: null,
                deductedAmount: null,
                modifiedAmount: null,
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
