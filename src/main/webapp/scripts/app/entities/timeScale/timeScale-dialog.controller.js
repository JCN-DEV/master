'use strict';

angular.module('stepApp').controller('TimeScaleDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'TimeScale', 'PayScale',
        function($scope, $stateParams, $modalInstance, entity, TimeScale, PayScale) {

        $scope.timeScale = entity;
        $scope.payscales = PayScale.query({size: 500});
        $scope.load = function(id) {
            TimeScale.get({id : id}, function(result) {
                $scope.timeScale = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:timeScaleUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.timeScale.id != null) {
                TimeScale.update($scope.timeScale, onSaveSuccess, onSaveError);
            } else {
                TimeScale.save($scope.timeScale, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
