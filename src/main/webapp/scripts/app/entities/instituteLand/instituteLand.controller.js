'use strict';

angular.module('stepApp')
    .controller('InstituteLandController',
    ['$scope', '$state', '$modal', 'InstituteLand', 'InstituteLandSearch', 'ParseLinks',
    function ($scope, $state, $modal, InstituteLand, InstituteLandSearch, ParseLinks) {

        $scope.instituteLands = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            InstituteLand.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.instituteLands = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            InstituteLandSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.instituteLands = result;
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
            $scope.instituteLand = {
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
