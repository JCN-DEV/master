'use strict';

angular.module('stepApp').controller('InstCategoryDialogController',
    ['$scope', '$rootScope','$stateParams', '$modalInstance', 'entity', 'InstCategory',
        function($scope, $rootScope, $stateParams, $modalInstance, entity, InstCategory) {

        $scope.instCategory = entity;
        $scope.load = function(id) {
            InstCategory.get({id : id}, function(result) {
                $scope.instCategory = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:instCategoryUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.instCategory.id != null) {
                InstCategory.update($scope.instCategory, onSaveSuccess, onSaveError);
                $rootScope.setWarningMessage('stepApp.instCategory.updated');
            } else {
                InstCategory.save($scope.instCategory, onSaveSuccess, onSaveError);
                $rootScope.setSuccessMessage('stepApp.instCategory.created');

            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
