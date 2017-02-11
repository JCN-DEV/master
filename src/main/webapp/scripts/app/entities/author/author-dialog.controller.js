'use strict';

angular.module('stepApp').controller('AuthorDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'DataUtils', 'entity', 'Author', 'User',
        function($scope, $stateParams, $modalInstance, DataUtils, entity, Author, User) {

        $scope.author = entity;
        $scope.users = User.query({size: 500});
        $scope.load = function(id) {
            Author.get({id : id}, function(result) {
                $scope.author = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:authorUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.author.id != null) {
                Author.update($scope.author, onSaveSuccess, onSaveError);
            } else {
                Author.save($scope.author, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };

        $scope.abbreviate = DataUtils.abbreviate;

        $scope.byteSize = DataUtils.byteSize;

        $scope.setPhoto = function ($file, author) {
            if ($file && $file.$error == 'pattern') {
                return;
            }
            if ($file) {
                var fileReader = new FileReader();
                fileReader.readAsDataURL($file);
                fileReader.onload = function (e) {
                    var base64Data = e.target.result.substr(e.target.result.indexOf('base64,') + 'base64,'.length);
                    $scope.$apply(function() {
                        author.photo = base64Data;
                        author.photoContentType = $file.type;
                    });
                };
            }
        };
}]);
