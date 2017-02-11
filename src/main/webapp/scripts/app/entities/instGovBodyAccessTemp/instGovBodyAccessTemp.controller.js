'use strict';

angular.module('stepApp')
    .controller('InstGovBodyAccessTempController', function ($scope, InstGovBodyAccessTemp, InstGovBodyAccessTempSearch, ParseLinks) {
        $scope.instGovBodyAccessTemps = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            InstGovBodyAccessTemp.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.instGovBodyAccessTemps = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            InstGovBodyAccessTemp.get({id: id}, function(result) {
                $scope.instGovBodyAccessTemp = result;
                $('#deleteInstGovBodyAccessTempConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            InstGovBodyAccessTemp.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteInstGovBodyAccessTempConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            InstGovBodyAccessTempSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.instGovBodyAccessTemps = result;
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
            $scope.instGovBodyAccessTemp = {
                dateCreated: null,
                dateModified: null,
                status: null,
                id: null
            };
        };
    });
