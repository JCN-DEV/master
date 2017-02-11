'use strict';

angular.module('stepApp').controller('BookDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Book', 'Author',
        function($scope, $stateParams, $modalInstance, entity, Book, Author) {

        $scope.book = entity;
        $scope.authors = Author.query({size: 500});
        $scope.load = function(id) {
            Book.get({id : id}, function(result) {
                $scope.book = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:bookUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.book.id != null) {
                Book.update($scope.book, onSaveSuccess, onSaveError);
            } else {
                Book.save($scope.book, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
