'use strict';

angular.module('stepApp')
    .controller('InstBuildingTempController', function ($scope, InstBuildingTemp, InstBuildingTempSearch, ParseLinks) {
        $scope.instBuildingTemps = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            InstBuildingTemp.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.instBuildingTemps = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            InstBuildingTemp.get({id: id}, function(result) {
                $scope.instBuildingTemp = result;
                $('#deleteInstBuildingTempConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            InstBuildingTemp.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteInstBuildingTempConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            InstBuildingTempSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.instBuildingTemps = result;
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
            $scope.instBuildingTemp = {
                totalArea: null,
                totalRoom: null,
                classRoom: null,
                officeRoom: null,
                otherRoom: null,
                id: null
            };
        };
    });
