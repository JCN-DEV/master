'use strict';

angular.module('stepApp')
    .controller('AssetCategorySetupController',
    ['$scope', '$rootScope', 'AssetCategorySetup', 'AssetCategorySetupSearch', 'ParseLinks',
    function ($scope, $rootScope, AssetCategorySetup, AssetCategorySetupSearch, ParseLinks) {
        $scope.assetCategorySetups = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            AssetCategorySetup.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.assetCategorySetups = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            AssetCategorySetup.get({id: id}, function(result) {
                $scope.assetCategorySetup = result;
                $('#deleteAssetCategorySetupConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            AssetCategorySetup.delete({id: id},
                function () {
                    $scope.loadAll();
                    $rootScope.setErrorMessage('stepApp.assetCategorySetup.deleted');
                    $('#deleteAssetCategorySetupConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            AssetCategorySetupSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.assetCategorySetups = result;
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
            $scope.assetCategorySetup = {
                categoryName: null,
                description: null,
                status: null,
                id: null
            };
        };
    }]);
