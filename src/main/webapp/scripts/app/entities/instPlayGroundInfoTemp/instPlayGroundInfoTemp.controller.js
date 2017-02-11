'use strict';

angular.module('stepApp')
    .controller('InstPlayGroundInfoTempController', function ($scope, InstPlayGroundInfoTemp, InstPlayGroundInfoTempSearch, ParseLinks) {
        $scope.instPlayGroundInfoTemps = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            InstPlayGroundInfoTemp.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.instPlayGroundInfoTemps = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            InstPlayGroundInfoTemp.get({id: id}, function(result) {
                $scope.instPlayGroundInfoTemp = result;
                $('#deleteInstPlayGroundInfoTempConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            InstPlayGroundInfoTemp.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteInstPlayGroundInfoTempConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            InstPlayGroundInfoTempSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.instPlayGroundInfoTemps = result;
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
            $scope.instPlayGroundInfoTemp = {
                playgroundNo: null,
                area: null,
                id: null
            };
        };
    });
