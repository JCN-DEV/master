'use strict';

angular.module('stepApp')
    .controller('FeeInvoiceController',
    ['$scope', 'FeeInvoice', 'FeeInvoiceSearch', 'ParseLinks',
    function ($scope, FeeInvoice, FeeInvoiceSearch, ParseLinks) {
        $scope.feeInvoices = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            FeeInvoice.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.feeInvoices = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            FeeInvoice.get({id: id}, function(result) {
                $scope.feeInvoice = result;
                $('#deleteFeeInvoiceConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            FeeInvoice.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteFeeInvoiceConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            FeeInvoiceSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.feeInvoices = result;
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
            $scope.feeInvoice = {
                invoiceId: null,
                bankName: null,
                bankAccountNo: null,
                description: null,
                createDate: null,
                createBy: null,
                id: null
            };
        };
    }]);
