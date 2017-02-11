'use strict';

angular.module('stepApp')
    .controller('AssetTransferController',
    ['$scope', '$rootScope', 'AssetTransfer', 'AssetTransferSearch', 'ParseLinks',
    function ($scope, $rootScope, AssetTransfer, AssetTransferSearch, ParseLinks) {
        $scope.assetTransfers = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            AssetTransfer.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.assetTransfers = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            AssetTransfer.get({id: id}, function(result) {
                $scope.assetTransfer = result;
                $('#deleteAssetTransferConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            AssetTransfer.delete({id: id},
                function () {
                    $scope.loadAll();
                    $rootScope.setErrorMessage('stepApp.assetTransfer.deleted');
                    $('#deleteAssetTransferConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            AssetTransferSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.assetTransfers = result;
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
            $scope.assetTransfer = {
                date: null,
                id: null
            };
        };
    }]);
