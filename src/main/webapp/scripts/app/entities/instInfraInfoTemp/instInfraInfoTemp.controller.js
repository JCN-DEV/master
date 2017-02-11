'use strict';

angular.module('stepApp')
    .controller('InstInfraInfoTempController', function ($scope, InstInfraInfoTemp, InstInfraInfoTempSearch, ParseLinks) {
        $scope.instInfraInfoTemps = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            InstInfraInfoTemp.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.instInfraInfoTemps = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            InstInfraInfoTemp.get({id: id}, function(result) {
                $scope.instInfraInfoTemp = result;
                $('#deleteInstInfraInfoTempConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            InstInfraInfoTemp.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteInstInfraInfoTempConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            InstInfraInfoTempSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.instInfraInfoTemps = result;
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
            $scope.instInfraInfoTemp = {
                status: null,
                id: null
            };
        };
    });
