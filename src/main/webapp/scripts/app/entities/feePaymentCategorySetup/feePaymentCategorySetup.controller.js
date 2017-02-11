'use strict';

angular.module('stepApp')
    .controller('FeePaymentCategorySetupController',
    ['$scope', 'FeePaymentCategorySetup', 'FeePaymentCategorySetupSearch', 'ParseLinks',
     function ($scope, FeePaymentCategorySetup, FeePaymentCategorySetupSearch, ParseLinks) {
        $scope.feePaymentCategorySetups = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            FeePaymentCategorySetup.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.feePaymentCategorySetups = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            FeePaymentCategorySetup.get({id: id}, function(result) {
                $scope.feePaymentCategorySetup = result;
                $('#deleteFeePaymentCategorySetupConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            FeePaymentCategorySetup.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteFeePaymentCategorySetupConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            FeePaymentCategorySetupSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.feePaymentCategorySetups = result;
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
            $scope.feePaymentCategorySetup = {
                paymentCategoryId: null,
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
