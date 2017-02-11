'use strict';

angular.module('stepApp')
    .controller('ResultController',
    ['$scope', '$state', '$modal', 'Result', 'ResultSearch', 'ParseLinks',
     function ($scope, $state, $modal, Result, ResultSearch, ParseLinks) {

        $scope.results = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Result.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.results = result;
                $scope.total = headers('x-total-count');
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };

        $scope.loadAll();

        $scope.search = function () {
            ResultSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.results = result;
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
            $scope.result = {
                year: null,
                result: null,
                remarks: null,
                id: null
            };
        };

        // bulk operations start
        $scope.areAllResultsSelected = false;

        $scope.updateResultsSelection = function (resultArray, selectionValue) {
            for (var i = 0; i < resultArray.length; i++)
            {
            resultArray[i].isSelected = selectionValue;
            }
        };


        $scope.import = function (){
            for (var i = 0; i < $scope.results.length; i++){
                var result = $scope.results[i];
                if(result.isSelected){
                    //Result.update(result);
                    //TODO: handle bulk export
                }
            }
        };

        $scope.export = function (){
            for (var i = 0; i < $scope.results.length; i++){
                var result = $scope.results[i];
                if(result.isSelected){
                    //Result.update(result);
                    //TODO: handle bulk export
                }
            }
        };

        $scope.deleteSelected = function (){
            for (var i = 0; i < $scope.results.length; i++){
                var result = $scope.results[i];
                if(result.isSelected){
                    Result.delete(result);
                }
            }
        };

        $scope.sync = function (){
            for (var i = 0; i < $scope.results.length; i++){
                var result = $scope.results[i];
                if(result.isSelected){
                    Result.update(result);
                }
            }
        };

        $scope.order = function (predicate, reverse) {
            $scope.predicate = predicate;
            $scope.reverse = reverse;
            Result.query({page: $scope.page, size: 20}, function (result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.results = result;
                $scope.total = headers('x-total-count');
            });
        };
        // bulk operations end

    }]);
