'use strict';

angular.module('stepApp')
    .controller('AssetRepairController',
    ['$scope', '$rootScope', 'AssetRepair', 'AssetRepairSearch', 'ParseLinks',
    function ($scope, $rootScope, AssetRepair, AssetRepairSearch, ParseLinks) {
        $scope.assetRepairs = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            AssetRepair.query({page: $scope.page, size: 5000}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.assetRepairs = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            AssetRepair.get({id: id}, function(result) {
                $scope.assetRepair = result;
                $('#deleteAssetRepairConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            AssetRepair.delete({id: id},
                function () {
                    $scope.loadAll();
                    $rootScope.setErrorMessage('stepApp.assetRepair.deleted');
                    $('#deleteAssetRepairConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            AssetRepairSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.assetRepairs = result;
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
            $scope.assetRepair = {
                refNo: null,
                repairedBy: null,
                dateOfProblem: null,
                repairDate: null,
                repairCost: null,
                id: null
            };
        };
    }]);
