'use strict';

angular.module('stepApp').controller('BEdApplicationEditLogDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'BEdApplicationEditLog', 'BEdApplication',
        function($scope, $stateParams, $modalInstance, entity, BEdApplicationEditLog, BEdApplication) {

        $scope.bEdApplicationEditLog = entity;
        $scope.bedapplications = BEdApplication.query();
        $scope.load = function(id) {
            BEdApplicationEditLog.get({id : id}, function(result) {
                $scope.bEdApplicationEditLog = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:bEdApplicationEditLogUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.bEdApplicationEditLog.id != null) {
                BEdApplicationEditLog.update($scope.bEdApplicationEditLog, onSaveSuccess, onSaveError);
            } else {
                BEdApplicationEditLog.save($scope.bEdApplicationEditLog, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
