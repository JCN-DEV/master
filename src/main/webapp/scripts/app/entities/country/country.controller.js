'use strict';

angular.module('stepApp')
    .controller('CountryController',
    ['$scope', 'Country', 'CountrySearch', 'ParseLinks',
    function ($scope, Country, CountrySearch, ParseLinks) {
        $scope.countrys = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Country.query({page: $scope.page, size: 5000}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.countrys = result;
                $scope.total = headers('x-total-count');
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            Country.get({id: id}, function(result) {
                $scope.country = result;
                $('#deleteCountryConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Country.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteCountryConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            CountrySearch.query({query: $scope.searchQuery}, function(result) {
                $scope.countrys = result;
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


        //==================|
        // bulk actions		|
        //==================|
        $scope.areAllCountrysSelected = false;

        $scope.updateCountrysSelection = function (countryArray, selectionValue) {
            for (var i = 0; i < countryArray.length; i++) {
                countryArray[i].isSelected = selectionValue;
            }
        };

        $scope.sync = function () {
            for (var i = 0; i < $scope.countrys.length; i++) {
                var country = $scope.countrys[i];
                if (country.isSelected) {
                    Country.update(country);
                }
            }
        };

        $scope.clear = function () {
            $scope.country = {
                isoCode3: null,
                isoCode2: null,
                name: null,
                continent: null,
                region: null,
                surfaceArea: null,
                indepYear: null,
                population: null,
                lifeExpectancy: null,
                gnp: null,
                gnpOld: null,
                localName: null,
                governmentform: null,
                capital: null,
                headOfState: null,
                callingCode: null,
                id: null
            };
        };
    }]);
