'use strict';

angular.module('stepApp').controller('BEdApplicationStatusLogDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'BEdApplicationStatusLog', 'BEdApplication',
        function($scope, $stateParams, $modalInstance, entity, BEdApplicationStatusLog, BEdApplication) {

        $scope.bEdApplicationStatusLog = entity;
        $scope.bedapplications = BEdApplication.query();
        $scope.load = function(id) {
            BEdApplicationStatusLog.get({id : id}, function(result) {
                $scope.bEdApplicationStatusLog = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:bEdApplicationStatusLogUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.bEdApplicationStatusLog.id != null) {
                BEdApplicationStatusLog.update($scope.bEdApplicationStatusLog, onSaveSuccess, onSaveError);
            } else {
                BEdApplicationStatusLog.save($scope.bEdApplicationStatusLog, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
