'use strict';

angular.module('stepApp')
    .controller('FeePaymentTypeSetupController',
    ['$scope','FeePaymentTypeSetup','FeePaymentTypeSetupSearch','ParseLinks',
     function ($scope, FeePaymentTypeSetup, FeePaymentTypeSetupSearch, ParseLinks) {
        $scope.feePaymentTypeSetups = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            FeePaymentTypeSetup.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.feePaymentTypeSetups = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            FeePaymentTypeSetup.get({id: id}, function(result) {
                $scope.feePaymentTypeSetup = result;
                $('#deleteFeePaymentTypeSetupConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            FeePaymentTypeSetup.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteFeePaymentTypeSetupConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            FeePaymentTypeSetupSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.feePaymentTypeSetups = result;
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
            $scope.feePaymentTypeSetup = {
                paymentTypeId: null,
                name: null,
                status: null,
                description: null,
                createDate: null,
                createBy: null,
                updateBy: null,
                updateDate: null,
                id: null
            };
        };
    }]);
