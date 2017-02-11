'use strict';

angular.module('stepApp').controller('MpoApplicationEditLogDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'MpoApplicationEditLog', 'MpoApplication',
        function($scope, $stateParams, $modalInstance, entity, MpoApplicationEditLog, MpoApplication) {

        $scope.mpoApplicationEditLog = entity;
        $scope.mpoapplications = MpoApplication.query();
        $scope.load = function(id) {
            MpoApplicationEditLog.get({id : id}, function(result) {
                $scope.mpoApplicationEditLog = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:mpoApplicationEditLogUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.mpoApplicationEditLog.id != null) {
                MpoApplicationEditLog.update($scope.mpoApplicationEditLog, onSaveSuccess, onSaveError);
            } else {
                MpoApplicationEditLog.save($scope.mpoApplicationEditLog, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
