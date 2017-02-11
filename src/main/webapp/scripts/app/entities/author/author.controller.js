'use strict';

angular.module('stepApp')
    .controller('AuthorController',
    ['$scope', '$state', '$modal', 'DataUtils', 'Author', 'AuthorSearch', 'ParseLinks',
    function ($scope, $state, $modal, DataUtils, Author, AuthorSearch, ParseLinks) {

        $scope.authors = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Author.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.authors = result;
                $scope.total = headers('x-total-count');
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };

        $scope.loadAll();

        $scope.search = function () {
            AuthorSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.authors = result;
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
            $scope.author = {
                name: null,
                status: null,
                photo: null,
                photoContentType: null,
                dateOfBirth: null,
                id: null
            };
        };

        $scope.abbreviate = DataUtils.abbreviate;

        $scope.byteSize = DataUtils.byteSize;

        // bulk operations start
        $scope.areAllAuthorsSelected = false;

        $scope.updateAuthorsSelection = function (authorArray, selectionValue) {
            for (var i = 0; i < authorArray.length; i++)
            {
            authorArray[i].isSelected = selectionValue;
            }
        };


        $scope.import = function (){
            for (var i = 0; i < $scope.authors.length; i++){
                var author = $scope.authors[i];
                if(author.isSelected){
                    //Author.update(author);
                    //TODO: handle bulk export
                }
            }
        };

        $scope.export = function (){
            for (var i = 0; i < $scope.authors.length; i++){
                var author = $scope.authors[i];
                if(author.isSelected){
                    //Author.update(author);
                    //TODO: handle bulk export
                }
            }
        };

        $scope.deleteSelected = function (){
            for (var i = 0; i < $scope.authors.length; i++){
                var author = $scope.authors[i];
                if(author.isSelected){
                    Author.delete(author);
                }
            }
        };

        $scope.sync = function (){
            for (var i = 0; i < $scope.authors.length; i++){
                var author = $scope.authors[i];
                if(author.isSelected){
                    Author.update(author);
                }
            }
        };

        $scope.order = function (predicate, reverse) {
            $scope.predicate = predicate;
            $scope.reverse = reverse;
            Author.query({page: $scope.page, size: 20}, function (result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.authors = result;
                $scope.total = headers('x-total-count');
            });
        };
        // bulk operations end

    }]);
