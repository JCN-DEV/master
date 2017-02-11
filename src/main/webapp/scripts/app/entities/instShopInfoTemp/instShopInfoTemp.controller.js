'use strict';

angular.module('stepApp')
    .controller('InstShopInfoTempController', function ($scope, InstShopInfoTemp, InstShopInfoTempSearch, ParseLinks) {
        $scope.instShopInfoTemps = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            InstShopInfoTemp.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.instShopInfoTemps = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            InstShopInfoTemp.get({id: id}, function(result) {
                $scope.instShopInfoTemp = result;
                $('#deleteInstShopInfoTempConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            InstShopInfoTemp.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteInstShopInfoTempConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            InstShopInfoTempSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.instShopInfoTemps = result;
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
            $scope.instShopInfoTemp = {
                nameOrNumber: null,
                buildingNameOrNumber: null,
                length: null,
                width: null,
                id: null
            };
        };
    });
