'use strict';

angular.module('stepApp')
    .controller('AssetDestroyController',
    ['$scope', 'AssetDestroy', 'AssetDestroySearch', 'ParseLinks',
    function ($scope, AssetDestroy, AssetDestroySearch, ParseLinks) {
        $scope.assetDestroys = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            AssetDestroy.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.assetDestroys = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            AssetDestroy.get({id: id}, function(result) {
                $scope.assetDestroy = result;
                $('#deleteAssetDestroyConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            AssetDestroy.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteAssetDestroyConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            AssetDestroySearch.query({query: $scope.searchQuery}, function(result) {
                $scope.assetDestroys = result;
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
            $scope.assetDestroy = {
                transferReference: null,
                destroyDate: null,
                usedPeriod: null,
                causeOfDate: null,
                id: null
            };
        };
    }]);
