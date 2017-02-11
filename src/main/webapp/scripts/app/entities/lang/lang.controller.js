'use strict';

angular.module('stepApp')
    .controller('LangController',
    ['$scope', '$state', '$modal', 'Lang', 'LangSearch', 'ParseLinks',
    function ($scope, $state, $modal, Lang, LangSearch, ParseLinks) {

        $scope.langs = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Lang.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.langs = result;
                $scope.total = headers('x-total-count');
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };

        $scope.loadAll();

        $scope.search = function () {
            LangSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.langs = result;
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
            $scope.lang = {
                name: null,
                reading: null,
                writing: null,
                speaking: null,
                listening: null,
                status: null,
                id: null
            };
        };

        // bulk operations start
        $scope.areAllLangsSelected = false;

        $scope.updateLangsSelection = function (langArray, selectionValue) {
            for (var i = 0; i < langArray.length; i++)
            {
            langArray[i].isSelected = selectionValue;
            }
        };


        $scope.import = function (){
            for (var i = 0; i < $scope.langs.length; i++){
                var lang = $scope.langs[i];
                if(lang.isSelected){
                    //Lang.update(lang);
                    //TODO: handle bulk export
                }
            }
        };

        $scope.export = function (){
            for (var i = 0; i < $scope.langs.length; i++){
                var lang = $scope.langs[i];
                if(lang.isSelected){
                    //Lang.update(lang);
                    //TODO: handle bulk export
                }
            }
        };

        $scope.deleteSelected = function (){
            for (var i = 0; i < $scope.langs.length; i++){
                var lang = $scope.langs[i];
                if(lang.isSelected){
                    Lang.delete(lang);
                }
            }
        };

        $scope.sync = function (){
            for (var i = 0; i < $scope.langs.length; i++){
                var lang = $scope.langs[i];
                if(lang.isSelected){
                    Lang.update(lang);
                }
            }
        };

        $scope.order = function (predicate, reverse) {
            $scope.predicate = predicate;
            $scope.reverse = reverse;
            Lang.query({page: $scope.page, size: 20}, function (result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.langs = result;
                $scope.total = headers('x-total-count');
            });
        };
        // bulk operations end

    }]);
