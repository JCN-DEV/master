'use strict';

angular.module('stepApp')
    .controller('UpazilaController',
    ['$scope', '$state', '$modal', 'Upazila', 'UpazilaSearch', 'ParseLinks',
    function ($scope, $state, $modal, Upazila, UpazilaSearch, ParseLinks) {

        $scope.upazilas = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Upazila.query({page: $scope.page, size: 500}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.upazilas = result;
                $scope.total = headers('x-total-count');
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };

        $scope.loadAll();

        $scope.search = function () {
            UpazilaSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.upazilas = result;
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
            $scope.upazila = {
                name: null,
                bnName: null,
                id: null
            };
        };

        // bulk operations start
        $scope.areAllUpazilasSelected = false;

        $scope.updateUpazilasSelection = function (upazilaArray, selectionValue) {
            for (var i = 0; i < upazilaArray.length; i++)
            {
            upazilaArray[i].isSelected = selectionValue;
            }
        };


        $scope.import = function (){
            for (var i = 0; i < $scope.upazilas.length; i++){
                var upazila = $scope.upazilas[i];
                if(upazila.isSelected){
                    //Upazila.update(upazila);
                    //TODO: handle bulk export
                }
            }
        };

        $scope.export = function (){
            for (var i = 0; i < $scope.upazilas.length; i++){
                var upazila = $scope.upazilas[i];
                if(upazila.isSelected){
                    //Upazila.update(upazila);
                    //TODO: handle bulk export
                }
            }
        };

        $scope.deleteSelected = function (){
            for (var i = 0; i < $scope.upazilas.length; i++){
                var upazila = $scope.upazilas[i];
                if(upazila.isSelected){
                    Upazila.delete(upazila);
                }
            }
        };

        $scope.sync = function (){
            for (var i = 0; i < $scope.upazilas.length; i++){
                var upazila = $scope.upazilas[i];
                if(upazila.isSelected){
                    Upazila.update(upazila);
                }
            }
        };

        $scope.order = function (predicate, reverse) {
            $scope.predicate = predicate;
            $scope.reverse = reverse;
            Upazila.query({page: $scope.page, size: 20}, function (result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.upazilas = result;
                $scope.total = headers('x-total-count');
            });
        };
        // bulk operations end

    }]);
