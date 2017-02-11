'use strict';

angular.module('stepApp')
    .controller('InstLandTempController', function ($scope, InstLandTemp, InstLandTempSearch, ParseLinks) {
        $scope.instLandTemps = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            InstLandTemp.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.instLandTemps = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            InstLandTemp.get({id: id}, function(result) {
                $scope.instLandTemp = result;
                $('#deleteInstLandTempConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            InstLandTemp.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteInstLandTempConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            InstLandTempSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.instLandTemps = result;
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
            $scope.instLandTemp = {
                mouja: null,
                jlNo: null,
                ledgerNo: null,
                dagNo: null,
                amountOfLand: null,
                landRegistrationLedgerNo: null,
                landRegistrationDate: null,
                lastTaxPaymentDate: null,
                boundaryNorth: null,
                boundarySouth: null,
                boundaryEast: null,
                boundaryWest: null,
                id: null
            };
        };
    });
