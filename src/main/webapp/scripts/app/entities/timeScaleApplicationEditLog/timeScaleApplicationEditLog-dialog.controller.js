'use strict';

angular.module('stepApp').controller('TimeScaleApplicationEditLogDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'TimeScaleApplicationEditLog', 'TimeScaleApplication',
        function($scope, $stateParams, $modalInstance, entity, TimeScaleApplicationEditLog, TimeScaleApplication) {

        $scope.timeScaleApplicationEditLog = entity;
        $scope.timescaleapplications = TimeScaleApplication.query();
        $scope.load = function(id) {
            TimeScaleApplicationEditLog.get({id : id}, function(result) {
                $scope.timeScaleApplicationEditLog = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:timeScaleApplicationEditLogUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.timeScaleApplicationEditLog.id != null) {
                TimeScaleApplicationEditLog.update($scope.timeScaleApplicationEditLog, onSaveSuccess, onSaveError);
            } else {
                TimeScaleApplicationEditLog.save($scope.timeScaleApplicationEditLog, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
