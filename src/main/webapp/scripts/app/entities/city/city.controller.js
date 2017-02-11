'use strict';

angular.module('stepApp')
    .controller('CityController',
    ['$scope', '$state', '$modal', 'City', 'CitySearch', 'ParseLinks',
    function ($scope, $state, $modal, City, CitySearch, ParseLinks) {

        $scope.citys = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            City.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.citys = result;
                $scope.total = headers('x-total-count');
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };

        $scope.loadAll();

        $scope.search = function () {
            CitySearch.query({query: $scope.searchQuery}, function(result) {
                $scope.citys = result;
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
            $scope.city = {
                name: null,
                dist: null,
                population: null,
                id: null
            };
        };

        // bulk operations start
        $scope.areAllCitysSelected = false;

        $scope.updateCitysSelection = function (cityArray, selectionValue) {
            for (var i = 0; i < cityArray.length; i++)
            {
            cityArray[i].isSelected = selectionValue;
            }
        };


        $scope.import = function (){
            for (var i = 0; i < $scope.citys.length; i++){
                var city = $scope.citys[i];
                if(city.isSelected){
                    //City.update(city);
                    //TODO: handle bulk export
                }
            }
        };

        $scope.export = function (){
            for (var i = 0; i < $scope.citys.length; i++){
                var city = $scope.citys[i];
                if(city.isSelected){
                    //City.update(city);
                    //TODO: handle bulk export
                }
            }
        };

        $scope.deleteSelected = function (){
            for (var i = 0; i < $scope.citys.length; i++){
                var city = $scope.citys[i];
                if(city.isSelected){
                    City.delete(city);
                }
            }
        };

        $scope.sync = function (){
            for (var i = 0; i < $scope.citys.length; i++){
                var city = $scope.citys[i];
                if(city.isSelected){
                    City.update(city);
                }
            }
        };

        $scope.order = function (predicate, reverse) {
            $scope.predicate = predicate;
            $scope.reverse = reverse;
            City.query({page: $scope.page, size: 20}, function (result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.citys = result;
                $scope.total = headers('x-total-count');
            });
        };
        // bulk operations end

    }]);
