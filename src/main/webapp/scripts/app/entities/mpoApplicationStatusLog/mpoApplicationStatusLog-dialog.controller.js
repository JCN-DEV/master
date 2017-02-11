'use strict';

angular.module('stepApp').controller('MpoApplicationStatusLogDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'MpoApplicationStatusLog', 'MpoApplication',
        function($scope, $stateParams, $modalInstance, entity, MpoApplicationStatusLog, MpoApplication) {

        $scope.mpoApplicationStatusLog = entity;
        $scope.mpoapplications = MpoApplication.query();
        $scope.load = function(id) {
            MpoApplicationStatusLog.get({id : id}, function(result) {
                $scope.mpoApplicationStatusLog = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:mpoApplicationStatusLogUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.mpoApplicationStatusLog.id != null) {
                MpoApplicationStatusLog.update($scope.mpoApplicationStatusLog, onSaveSuccess, onSaveError);
            } else {
                MpoApplicationStatusLog.save($scope.mpoApplicationStatusLog, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
