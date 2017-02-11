'use strict';

angular.module('stepApp').controller('TimeScaleApplicationStatusLogDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'TimeScaleApplicationStatusLog', 'TimeScaleApplication',
        function($scope, $stateParams, $modalInstance, entity, TimeScaleApplicationStatusLog, TimeScaleApplication) {

        $scope.timeScaleApplicationStatusLog = entity;
        $scope.timescaleapplications = TimeScaleApplication.query();
        $scope.load = function(id) {
            TimeScaleApplicationStatusLog.get({id : id}, function(result) {
                $scope.timeScaleApplicationStatusLog = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:timeScaleApplicationStatusLogUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.timeScaleApplicationStatusLog.id != null) {
                TimeScaleApplicationStatusLog.update($scope.timeScaleApplicationStatusLog, onSaveSuccess, onSaveError);
            } else {
                TimeScaleApplicationStatusLog.save($scope.timeScaleApplicationStatusLog, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
