'use strict';

angular.module('stepApp').controller('BoardNameDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'BoardName',
        function($scope, $stateParams, $modalInstance, entity, BoardName) {

        $scope.boardName = entity;
        $scope.load = function(id) {
            BoardName.get({id : id}, function(result) {
                $scope.boardName = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:boardNameUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.boardName.id != null) {
                BoardName.update($scope.boardName, onSaveSuccess, onSaveError);
            } else {
                BoardName.save($scope.boardName, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
