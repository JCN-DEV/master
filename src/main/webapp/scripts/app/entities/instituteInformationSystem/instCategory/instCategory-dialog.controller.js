'use strict';

angular.module('stepApp').controller('InstCategoryDialogController',
    ['$scope','$rootScope', '$state', '$stateParams', 'entity', 'InstCategory',
        function($scope, $rootScope, $state, $stateParams, entity, InstCategory) {

        $scope.instCategory = entity;
            $scope.instCategory.pStatus = true;
        $scope.load = function(id) {
            InstCategory.get({id : id}, function(result) {
                $scope.instCategory = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:instCategoryUpdate', result);
            //$modalInstance.close(result);
            $scope.isSaving = false;
            $state.go('setup.instCategory',{},{reload:true});
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.instCategory.id != null) {
                InstCategory.update($scope.instCategory, onSaveSuccess, onSaveError);
                $rootScope.setSuccessMessage('stepApp.instCategory.updated');
            } else {
                InstCategory.save($scope.instCategory, onSaveSuccess, onSaveError);
                $rootScope.setSuccessMessage('stepApp.instCategory.created');
            }
        };

        $scope.clear = function() {
           $scope.instCategory = null;
        };
}]);
