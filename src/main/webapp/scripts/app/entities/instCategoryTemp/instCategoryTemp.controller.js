'use strict';

angular.module('stepApp')
    .controller('InstCategoryTempController', function ($scope, InstCategoryTemp, InstCategoryTempSearch, ParseLinks) {
        $scope.instCategoryTemps = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            InstCategoryTemp.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.instCategoryTemps = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            InstCategoryTemp.get({id: id}, function(result) {
                $scope.instCategoryTemp = result;
                $('#deleteInstCategoryTempConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            InstCategoryTemp.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteInstCategoryTempConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            InstCategoryTempSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.instCategoryTemps = result;
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
            $scope.instCategoryTemp = {
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
