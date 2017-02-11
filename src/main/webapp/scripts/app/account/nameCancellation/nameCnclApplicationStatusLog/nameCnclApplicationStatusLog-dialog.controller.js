'use strict';

angular.module('stepApp').controller('NameCnclApplicationStatusLogDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'NameCnclApplicationStatusLog', 'NameCnclApplication',
        function($scope, $stateParams, $modalInstance, entity, NameCnclApplicationStatusLog, NameCnclApplication) {

        $scope.nameCnclApplicationStatusLog = entity;
        $scope.namecnclapplications = NameCnclApplication.query();
        $scope.load = function(id) {
            NameCnclApplicationStatusLog.get({id : id}, function(result) {
                $scope.nameCnclApplicationStatusLog = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:nameCnclApplicationStatusLogUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.nameCnclApplicationStatusLog.id != null) {
                NameCnclApplicationStatusLog.update($scope.nameCnclApplicationStatusLog, onSaveSuccess, onSaveError);
            } else {
                NameCnclApplicationStatusLog.save($scope.nameCnclApplicationStatusLog, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
