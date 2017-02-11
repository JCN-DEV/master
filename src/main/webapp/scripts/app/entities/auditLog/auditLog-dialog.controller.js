'use strict';

angular.module('stepApp').controller('AuditLogDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'AuditLog',
        function($scope, $stateParams, $modalInstance, entity, AuditLog) {

        $scope.auditLog = entity;
        $scope.load = function(id) {
            AuditLog.get({id : id}, function(result) {
                $scope.auditLog = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('stepApp:auditLogUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.auditLog.id != null) {
                AuditLog.update($scope.auditLog, onSaveFinished);
            } else {
                AuditLog.save($scope.auditLog, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
