'use strict';

angular.module('stepApp')
    .controller('InstMemShipController',
    ['$scope', '$state', '$modal', 'InstMemShip', 'InstMemShipSearch', 'ParseLinks',
    function ($scope, $state, $modal, InstMemShip, InstMemShipSearch, ParseLinks) {

        $scope.instMemShips = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            InstMemShip.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.instMemShips = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            InstMemShipSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.instMemShips = result;
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
            $scope.instMemShip = {
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
    }]);
