'use strict';

angular.module('stepApp')
    .controller('PfmsEmpAdjustmentController', function ($scope, PfmsEmpAdjustment, PfmsEmpAdjustmentSearch, ParseLinks) {
        $scope.pfmsEmpAdjustments = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            PfmsEmpAdjustment.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.pfmsEmpAdjustments = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            PfmsEmpAdjustment.get({id: id}, function(result) {
                $scope.pfmsEmpAdjustment = result;
                $('#deletePfmsEmpAdjustmentConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            PfmsEmpAdjustment.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deletePfmsEmpAdjustmentConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            PfmsEmpAdjustmentSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.pfmsEmpAdjustments = result;
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
            $scope.pfmsEmpAdjustment = {
                isCurrentBalance: false,
                ownContribute: null,
                ownContributeInt: null,
                preOwnContribute: null,
                preOwnContributeInt: null,
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
