'use strict';

angular.module('stepApp').controller('QrcodeGenLogDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'QrcodeGenLog',
        function($scope, $stateParams, $modalInstance, entity, QrcodeGenLog) {

        $scope.qrcodeGenLog = entity;
        $scope.load = function(id) {
            QrcodeGenLog.get({id : id}, function(result) {
                $scope.qrcodeGenLog = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:qrcodeGenLogUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.qrcodeGenLog.id != null) {
                QrcodeGenLog.update($scope.qrcodeGenLog, onSaveSuccess, onSaveError);
            } else {
                QrcodeGenLog.save($scope.qrcodeGenLog, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
