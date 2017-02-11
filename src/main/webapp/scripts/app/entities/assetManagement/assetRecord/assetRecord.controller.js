'use strict';

angular.module('stepApp')
    .controller('AssetRecordController',
    ['$scope', '$rootScope', 'AssetRecord', 'AssetRecordSearch', 'ParseLinks',
    function ($scope, $rootScope, AssetRecord, AssetRecordSearch, ParseLinks) {
        $scope.assetRecords = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            AssetRecord.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.assetRecords = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            AssetRecord.get({id: id}, function(result) {
                $scope.assetRecord = result;
                $rootScope.setErrorMessage('stepApp.assetRecord.deleted');
                $('#deleteAssetRecordConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            AssetRecord.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteAssetRecordConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            AssetRecordSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.assetRecords = result;
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
            $scope.assetRecord = {
                assetName: null,
                vendorName: null,
                supplierName: null,
                purchaseDate: null,
                orderNo: null,
                price: null,
                status: null,
                id: null
            };
        };
    }]);
