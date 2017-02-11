'use strict';

angular.module('stepApp').controller('AuditLogHistoryDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'AuditLogHistory', 'AuditLog',
        function($scope, $stateParams, $modalInstance, entity, AuditLogHistory, AuditLog) {

        $scope.auditLogHistory = entity;
        $scope.auditlogs = AuditLog.query();
        $scope.load = function(id) {
            AuditLogHistory.get({id : id}, function(result) {
                $scope.auditLogHistory = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('stepApp:auditLogHistoryUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.auditLogHistory.id != null) {
                AuditLogHistory.update($scope.auditLogHistory, onSaveFinished);
            } else {
                AuditLogHistory.save($scope.auditLogHistory, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
