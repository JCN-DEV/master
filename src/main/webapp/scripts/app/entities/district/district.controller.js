'use strict';

angular.module('stepApp')
    .controller('DistrictController',
    ['$scope', '$state', '$modal', 'District', 'DistrictSearch', 'ParseLinks',
    function ($scope, $state, $modal, District, DistrictSearch, ParseLinks) {

        $scope.districts = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            District.query({page: $scope.page, size: 100}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.districts = result;
                $scope.total = headers('x-total-count');
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };

        $scope.loadAll();

        $scope.search = function () {
            DistrictSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.districts = result;
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
            $scope.district = {
                name: null,
                bnName: null,
                lat: null,
                lon: null,
                website: null,
                id: null
            };
        };

        // bulk operations start
        $scope.areAllDistrictsSelected = false;

        $scope.updateDistrictsSelection = function (districtArray, selectionValue) {
            for (var i = 0; i < districtArray.length; i++)
            {
            districtArray[i].isSelected = selectionValue;
            }
        };


        $scope.import = function (){
            for (var i = 0; i < $scope.districts.length; i++){
                var district = $scope.districts[i];
                if(district.isSelected){
                    //District.update(district);
                    //TODO: handle bulk export
                }
            }
        };

        $scope.export = function (){
            for (var i = 0; i < $scope.districts.length; i++){
                var district = $scope.districts[i];
                if(district.isSelected){
                    //District.update(district);
                    //TODO: handle bulk export
                }
            }
        };

        $scope.deleteSelected = function (){
            for (var i = 0; i < $scope.districts.length; i++){
                var district = $scope.districts[i];
                if(district.isSelected){
                    District.delete(district);
                }
            }
        };

        $scope.sync = function (){
            for (var i = 0; i < $scope.districts.length; i++){
                var district = $scope.districts[i];
                if(district.isSelected){
                    District.update(district);
                }
            }
        };

        $scope.order = function (predicate, reverse) {
            $scope.predicate = predicate;
            $scope.reverse = reverse;
            District.query({page: $scope.page, size: 20}, function (result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.districts = result;
                $scope.total = headers('x-total-count');
            });
        };
        // bulk operations end

    }]);
