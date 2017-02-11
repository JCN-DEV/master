'use strict';

angular.module('stepApp').controller('APScaleApplicationStatusLogDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'APScaleApplicationStatusLog', 'TimeScaleApplication',
        function($scope, $stateParams, $modalInstance, entity, APScaleApplicationStatusLog, TimeScaleApplication) {

        $scope.aPScaleApplicationStatusLog = entity;
        $scope.timescaleapplications = TimeScaleApplication.query();
        $scope.load = function(id) {
            APScaleApplicationStatusLog.get({id : id}, function(result) {
                $scope.aPScaleApplicationStatusLog = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:aPScaleApplicationStatusLogUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.aPScaleApplicationStatusLog.id != null) {
                APScaleApplicationStatusLog.update($scope.aPScaleApplicationStatusLog, onSaveSuccess, onSaveError);
            } else {
                APScaleApplicationStatusLog.save($scope.aPScaleApplicationStatusLog, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
