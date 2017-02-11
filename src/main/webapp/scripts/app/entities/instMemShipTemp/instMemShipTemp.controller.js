'use strict';

angular.module('stepApp')
    .controller('InstMemShipTempController', function ($scope, InstMemShipTemp, InstMemShipTempSearch, ParseLinks) {
        $scope.instMemShipTemps = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            InstMemShipTemp.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.instMemShipTemps = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            InstMemShipTemp.get({id: id}, function(result) {
                $scope.instMemShipTemp = result;
                $('#deleteInstMemShipTempConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            InstMemShipTemp.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteInstMemShipTempConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            InstMemShipTempSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.instMemShipTemps = result;
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
            $scope.instMemShipTemp = {
                fullName: null,
                dob: null,
                gender: null,
                address: null,
                email: null,
                contact: null,
                designation: null,
                orgName: null,
                orgAdd: null,
                orgContact: null,
                date: null,
                id: null
            };
        };
    });
