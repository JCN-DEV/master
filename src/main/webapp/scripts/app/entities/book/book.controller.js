'use strict';

angular.module('stepApp')
    .controller('BookController',
    ['$scope', '$state', '$modal', 'Book', 'BookSearch', 'ParseLinks',
     function ($scope, $state, $modal, Book, BookSearch, ParseLinks) {

        $scope.books = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Book.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.books = result;
                $scope.total = headers('x-total-count');
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };

        $scope.loadAll();

        $scope.search = function () {
            BookSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.books = result;
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
            $scope.book = {
                name: null,
                id: null
            };
        };

        // bulk operations start
        $scope.areAllBooksSelected = false;

        $scope.updateBooksSelection = function (bookArray, selectionValue) {
            for (var i = 0; i < bookArray.length; i++)
            {
            bookArray[i].isSelected = selectionValue;
            }
        };


        $scope.import = function (){
            for (var i = 0; i < $scope.books.length; i++){
                var book = $scope.books[i];
                if(book.isSelected){
                    //Book.update(book);
                    //TODO: handle bulk export
                }
            }
        };

        $scope.export = function (){
            for (var i = 0; i < $scope.books.length; i++){
                var book = $scope.books[i];
                if(book.isSelected){
                    //Book.update(book);
                    //TODO: handle bulk export
                }
            }
        };

        $scope.deleteSelected = function (){
            for (var i = 0; i < $scope.books.length; i++){
                var book = $scope.books[i];
                if(book.isSelected){
                    Book.delete(book);
                }
            }
        };

        $scope.sync = function (){
            for (var i = 0; i < $scope.books.length; i++){
                var book = $scope.books[i];
                if(book.isSelected){
                    Book.update(book);
                }
            }
        };

        $scope.order = function (predicate, reverse) {
            $scope.predicate = predicate;
            $scope.reverse = reverse;
            Book.query({page: $scope.page, size: 20}, function (result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.books = result;
                $scope.total = headers('x-total-count');
            });
        };
        // bulk operations end

    }]);
