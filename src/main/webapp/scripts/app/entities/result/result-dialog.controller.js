'use strict';

angular.module('stepApp').controller('ResultDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Result', 'Institute',
        function($scope, $stateParams, $modalInstance, entity, Result, Institute) {

        $scope.result = entity;
        $scope.institutes = Institute.query({size: 500});
        $scope.load = function(id) {
            Result.get({id : id}, function(result) {
                $scope.result = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:resultUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.result.id != null) {
                Result.update($scope.result, onSaveSuccess, onSaveError);
            } else {
                Result.save($scope.result, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
