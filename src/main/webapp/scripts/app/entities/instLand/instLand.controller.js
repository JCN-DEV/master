'use strict';

angular.module('stepApp')
    .controller('InstLandController',
    ['$scope', '$state', '$modal', 'InstLand', 'InstLandSearch', 'ParseLinks',
    function ($scope, $state, $modal, InstLand, InstLandSearch, ParseLinks) {

        $scope.instLands = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            InstLand.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.instLands = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            InstLandSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.instLands = result;
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
            $scope.instLand = {
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
    }]);
