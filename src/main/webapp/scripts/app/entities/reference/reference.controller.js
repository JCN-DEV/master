'use strict';

angular.module('stepApp')
    .controller('ReferenceController',
    ['$scope', '$state', '$modal', 'Reference', 'ReferenceSearch', 'ParseLinks',
    function ($scope, $state, $modal, Reference, ReferenceSearch, ParseLinks) {

        $scope.references = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Reference.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.references = result;
                $scope.total = headers('x-total-count');
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };

        $scope.loadAll();

        $scope.search = function () {
            ReferenceSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.references = result;
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
            $scope.reference = {
                name: null,
                email: null,
                organisation: null,
                designation: null,
                relation: null,
                phone: null,
                remarks: null,
                id: null
            };
        };

        // bulk operations start
        $scope.areAllReferencesSelected = false;

        $scope.updateReferencesSelection = function (referenceArray, selectionValue) {
            for (var i = 0; i < referenceArray.length; i++)
            {
            referenceArray[i].isSelected = selectionValue;
            }
        };


        $scope.import = function (){
            for (var i = 0; i < $scope.references.length; i++){
                var reference = $scope.references[i];
                if(reference.isSelected){
                    //Reference.update(reference);
                    //TODO: handle bulk export
                }
            }
        };

        $scope.export = function (){
            for (var i = 0; i < $scope.references.length; i++){
                var reference = $scope.references[i];
                if(reference.isSelected){
                    //Reference.update(reference);
                    //TODO: handle bulk export
                }
            }
        };

        $scope.deleteSelected = function (){
            for (var i = 0; i < $scope.references.length; i++){
                var reference = $scope.references[i];
                if(reference.isSelected){
                    Reference.delete(reference);
                }
            }
        };

        $scope.sync = function (){
            for (var i = 0; i < $scope.references.length; i++){
                var reference = $scope.references[i];
                if(reference.isSelected){
                    Reference.update(reference);
                }
            }
        };

        $scope.order = function (predicate, reverse) {
            $scope.predicate = predicate;
            $scope.reverse = reverse;
            Reference.query({page: $scope.page, size: 20}, function (result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.references = result;
                $scope.total = headers('x-total-count');
            });
        };
        // bulk operations end

    }]);
