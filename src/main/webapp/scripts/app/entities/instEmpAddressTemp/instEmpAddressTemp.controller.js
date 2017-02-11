'use strict';

angular.module('stepApp')
    .controller('InstEmpAddressTempController', function ($scope, InstEmpAddressTemp, InstEmpAddressTempSearch, ParseLinks) {
        $scope.instEmpAddressTemps = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            InstEmpAddressTemp.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.instEmpAddressTemps = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            InstEmpAddressTemp.get({id: id}, function(result) {
                $scope.instEmpAddressTemp = result;
                $('#deleteInstEmpAddressTempConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            InstEmpAddressTemp.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteInstEmpAddressTempConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            InstEmpAddressTempSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.instEmpAddressTemps = result;
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
            $scope.instEmpAddressTemp = {
                villageOrHouse: null,
                roadBlockSector: null,
                post: null,
                prevVillageOrHouse: null,
                prevRoadBlockSector: null,
                prevPost: null,
                status: null,
                id: null
            };
        };
    });
