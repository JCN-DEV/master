'use strict';

angular.module('stepApp')
    .controller('AssetAuctionInformationController',
    ['$scope', '$rootScope', 'AssetAuctionInformation', 'AssetAuctionInformationSearch', 'ParseLinks',
    function ($scope, $rootScope, AssetAuctionInformation, AssetAuctionInformationSearch, ParseLinks) {
        $scope.assetAuctionInformations = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            AssetAuctionInformation.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.assetAuctionInformations = result;
                console.log($scope.assetAuctionInformations);
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            AssetAuctionInformation.get({id: id}, function(result) {
                $scope.assetAuctionInformation = result;
                $rootScope.setErrorMessage('stepApp.assetAuctionInformation.deleted');
                $('#deleteAssetAuctionInformationConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            AssetAuctionInformation.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteAssetAuctionInformationConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            AssetAuctionInformationSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.assetAuctionInformations = result;
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
            $scope.assetAuctionInformation = {
                lastRepairDate: null,
                isAuctionBefore: null,
                id: null
            };
        };
    }]);
