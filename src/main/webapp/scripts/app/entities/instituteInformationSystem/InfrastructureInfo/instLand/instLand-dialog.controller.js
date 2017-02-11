'use strict';

angular.module('stepApp').controller('InstLandDialogController',
    ['$scope','$rootScope', '$stateParams', '$modalInstance', 'entity', 'InstLand',
        function($scope,$rootScope, $stateParams, $modalInstance, entity, InstLand) {

        $scope.instLand = entity;
        $scope.load = function(id) {
            InstLand.get({id : id}, function(result) {
                $scope.instLand = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:instLandUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.instLand.id != null) {
                InstLand.update($scope.instLand, onSaveSuccess, onSaveError);
                $rootScope.setSuccessMessage('stepApp.instLand.updated');
            } else {
                InstLand.save($scope.instLand, onSaveSuccess, onSaveError);
                $rootScope.setSuccessMessage('stepApp.instLand.created');
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
