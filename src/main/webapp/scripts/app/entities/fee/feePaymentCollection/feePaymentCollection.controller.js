'use strict';

angular.module('stepApp')
    .controller('FeePaymentCollectionController',
    ['$scope', 'FeePaymentCollection', 'FeePaymentCollectionSearch', 'ParseLinks',
    function ($scope, FeePaymentCollection, FeePaymentCollectionSearch, ParseLinks) {
        $scope.feePaymentCollections = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            FeePaymentCollection.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.feePaymentCollections = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            FeePaymentCollection.get({id: id}, function(result) {
                $scope.feePaymentCollection = result;
                $('#deleteFeePaymentCollectionConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            FeePaymentCollection.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteFeePaymentCollectionConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            FeePaymentCollectionSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.feePaymentCollections = result;
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
            $scope.feePaymentCollection = {
                transactionId: null,
                voucherNo: null,
                status: null,
                description: null,
                amount: null,
                createDate: null,
                createBy: null,
                paymentDate: null,
                bankName: null,
                bankAccountNo: null,
                paymentMethod: null,
                paymentApiID: null,
                paymentInstrumentType: null,
                currency: null,
                updatedBy: null,
                updatedTime: null,
                id: null
            };
        };
    }]);
