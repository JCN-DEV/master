'use strict';

angular.module('stepApp')
    .controller('InstLabInfoTempController', function ($scope, InstLabInfoTemp, InstLabInfoTempSearch, ParseLinks) {
        $scope.instLabInfoTemps = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            InstLabInfoTemp.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.instLabInfoTemps = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            InstLabInfoTemp.get({id: id}, function(result) {
                $scope.instLabInfoTemp = result;
                $('#deleteInstLabInfoTempConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            InstLabInfoTemp.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteInstLabInfoTempConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            InstLabInfoTempSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.instLabInfoTemps = result;
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
            $scope.instLabInfoTemp = {
                nameOrNumber: null,
                buildingNameOrNumber: null,
                length: null,
                width: null,
                totalBooks: null,
                id: null
            };
        };
    });
