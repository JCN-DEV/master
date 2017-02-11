'use strict';

angular.module('stepApp').controller('NameCnclApplicationEditLogDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'NameCnclApplicationEditLog', 'NameCnclApplication',
        function($scope, $stateParams, $modalInstance, entity, NameCnclApplicationEditLog, NameCnclApplication) {

        $scope.nameCnclApplicationEditLog = entity;
        $scope.namecnclapplications = NameCnclApplication.query();
        $scope.load = function(id) {
            NameCnclApplicationEditLog.get({id : id}, function(result) {
                $scope.nameCnclApplicationEditLog = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:nameCnclApplicationEditLogUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.nameCnclApplicationEditLog.id != null) {
                NameCnclApplicationEditLog.update($scope.nameCnclApplicationEditLog, onSaveSuccess, onSaveError);
            } else {
                NameCnclApplicationEditLog.save($scope.nameCnclApplicationEditLog, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
