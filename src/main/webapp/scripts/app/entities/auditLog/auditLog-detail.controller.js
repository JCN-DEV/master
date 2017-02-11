'use strict';

angular.module('stepApp')
    .controller('AuditLogDetailController', function ($scope, $rootScope, $stateParams, entity, AuditLog) {
        $scope.auditLog = entity;
        $scope.load = function (id) {
            AuditLog.get({id: id}, function(result) {
                $scope.auditLog = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:auditLogUpdate', function(event, result) {
            $scope.auditLog = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
