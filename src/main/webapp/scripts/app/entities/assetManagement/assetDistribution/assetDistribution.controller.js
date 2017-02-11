'use strict';

angular.module('stepApp')
    .controller('AssetDistributionController',
    ['$scope', 'AssetDistribution', 'AssetDistributionSearch', 'ParseLinks',
    function ($scope, AssetDistribution, AssetDistributionSearch, ParseLinks) {
        $scope.assetDistributions = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            AssetDistribution.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.assetDistributions = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();




        $scope.delete = function (id) {
            AssetDistribution.get({id: id}, function(result) {
                $scope.assetDistribution = result;
                $('#deleteAssetDistributionConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            AssetDistribution.delete({id: id},
                function () {
                    $scope.loadAll();
                    $rootScope.setErrorMessage('stepApp.assetDistribution.deleted');
                    $('#deleteAssetDistributionConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            AssetDistributionSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.assetDistributions = result;
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
            $scope.assetDistribution = {
                transferRef: null,
                assignedDdate: null,
                remartks: null,
                id: null
            };
        };
    }]);
