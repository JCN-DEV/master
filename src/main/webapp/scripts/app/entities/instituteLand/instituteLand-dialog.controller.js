'use strict';

angular.module('stepApp').controller('InstituteLandDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'InstituteLand',
        function($scope, $stateParams, $modalInstance, entity, InstituteLand) {

        $scope.instituteLand = entity;
        $scope.load = function(id) {
            InstituteLand.get({id : id}, function(result) {
                $scope.instituteLand = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:instituteLandUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.instituteLand.id != null) {
                InstituteLand.update($scope.instituteLand, onSaveSuccess, onSaveError);
            } else {
                InstituteLand.save($scope.instituteLand, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
