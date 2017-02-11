'use strict';

angular.module('stepApp')
    .controller('AssetSupplierController',
     ['$scope', '$rootScope', 'AssetSupplier', 'AssetSupplierSearch', 'ParseLinks',
     function ($scope, $rootScope, AssetSupplier, AssetSupplierSearch, ParseLinks) {
        $scope.assetSuppliers = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            AssetSupplier.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.assetSuppliers = result;
            });
        };

        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            AssetSupplier.get({id: id}, function(result) {
                $scope.assetSupplier = result;
                $('#deleteAssetSupplierConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            AssetSupplier.delete({id: id},
                function () {
                    $scope.loadAll();
                    $rootScope.setErrorMessage('stepApp.assetSupplier.deleted');
                    $('#deleteAssetSupplierConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            AssetSupplierSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.assetSuppliers = result;
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
            $scope.assetSupplier = {
                name: null,
                supplierId: null,
                address: null,
                products: null,
                contactNo: null,
                telephoneNo: null,
                email: null,
                webSite: null,
                faxNo: null,
                id: null
            };
        };
    }]);
