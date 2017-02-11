'use strict';

angular.module('stepApp')
    .controller('InstLevelTempController', function ($scope, InstLevelTemp, InstLevelTempSearch, ParseLinks) {
        $scope.instLevelTemps = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            InstLevelTemp.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.instLevelTemps = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            InstLevelTemp.get({id: id}, function(result) {
                $scope.instLevelTemp = result;
                $('#deleteInstLevelTempConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            InstLevelTemp.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteInstLevelTempConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            InstLevelTempSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.instLevelTemps = result;
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
            $scope.instLevelTemp = {
                code: null,
                name: null,
                description: null,
                pStatus: null,
                createdDate: null,
                updatedDate: null,
                createdBy: null,
                updatedBy: null,
                status: null,
                id: null
            };
        };
    });
