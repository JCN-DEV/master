'use strict';

angular.module('stepApp').controller('InstLevelDialogController',
    ['$scope', '$stateParams', '$rootScope', '$modalInstance', 'entity', 'InstLevel',
        function($scope, $stateParams, $rootScope, $modalInstance, entity, InstLevel) {

        $scope.instLevel = entity;
        $scope.load = function(id) {
            InstLevel.get({id : id}, function(result) {
                $scope.instLevel = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:instLevelUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.instLevel.id != null) {
                InstLevel.update($scope.instLevel, onSaveSuccess, onSaveError);
                $rootScope.setWarningMessage('stepApp.instLevel.updated');
            } else {
                InstLevel.save($scope.instLevel, onSaveSuccess, onSaveError);
                $rootScope.setSuccessMessage('stepApp.instLevel.created');
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
