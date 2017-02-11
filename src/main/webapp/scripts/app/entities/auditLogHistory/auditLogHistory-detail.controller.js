'use strict';

angular.module('stepApp')
    .controller('AuditLogHistoryDetailController', function ($scope, $rootScope, $stateParams, entity, AuditLogHistory, AuditLog) {
        $scope.auditLogHistory = entity;
        $scope.load = function (id) {
            AuditLogHistory.get({id: id}, function(result) {
                $scope.auditLogHistory = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:auditLogHistoryUpdate', function(event, result) {
            $scope.auditLogHistory = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
