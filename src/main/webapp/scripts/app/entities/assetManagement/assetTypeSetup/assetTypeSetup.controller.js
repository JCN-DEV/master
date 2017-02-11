'use strict';

angular.module('stepApp')
    .controller('AssetTypeSetupController',
    ['$scope', '$rootScope', 'AssetTypeSetup', 'AssetTypeSetupSearch', 'ParseLinks',
    function ($scope, $rootScope, AssetTypeSetup, AssetTypeSetupSearch, ParseLinks) {
        $scope.assetTypeSetups = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            AssetTypeSetup.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.assetTypeSetups = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            AssetTypeSetup.get({id: id}, function(result) {
                $scope.assetTypeSetup = result;
                $('#deleteAssetTypeSetupConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            AssetTypeSetup.delete({id: id},
                function () {
                    $scope.loadAll();
                    $rootScope.setErrorMessage('stepApp.assetTypeSetup.deleted');
                    $('#deleteAssetTypeSetupConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            AssetTypeSetupSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.assetTypeSetups = result;
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

        //$scope.redirect = function(){
        //    $location.url('scripts/app/entities/assetManagement/assetTypeSetup/assetTypeSetup-detail.html');
        //}

        $scope.clear = function () {
            $scope.assetTypeSetup = {
                typeName: null,
                description: null,
                status: null,
                id: null
            };
        };
    }]);
