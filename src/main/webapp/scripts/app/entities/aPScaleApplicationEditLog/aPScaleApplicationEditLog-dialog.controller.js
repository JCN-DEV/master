'use strict';

angular.module('stepApp').controller('APScaleApplicationEditLogDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'APScaleApplicationEditLog', 'TimeScaleApplication',
        function($scope, $stateParams, $modalInstance, entity, APScaleApplicationEditLog, TimeScaleApplication) {

        $scope.aPScaleApplicationEditLog = entity;
        $scope.timescaleapplications = TimeScaleApplication.query();
        $scope.load = function(id) {
            APScaleApplicationEditLog.get({id : id}, function(result) {
                $scope.aPScaleApplicationEditLog = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:aPScaleApplicationEditLogUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.aPScaleApplicationEditLog.id != null) {
                APScaleApplicationEditLog.update($scope.aPScaleApplicationEditLog, onSaveSuccess, onSaveError);
            } else {
                APScaleApplicationEditLog.save($scope.aPScaleApplicationEditLog, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
