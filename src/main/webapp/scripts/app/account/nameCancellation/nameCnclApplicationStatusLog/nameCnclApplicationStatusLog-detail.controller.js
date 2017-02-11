'use strict';

angular.module('stepApp')
    .controller('NameCnclApplicationStatusLogDetailController', function ($scope, $rootScope, $stateParams, entity, NameCnclApplicationStatusLog, NameCnclApplication) {
        $scope.nameCnclApplicationStatusLog = entity;
        $scope.load = function (id) {
            NameCnclApplicationStatusLog.get({id: id}, function(result) {
                $scope.nameCnclApplicationStatusLog = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:nameCnclApplicationStatusLogUpdate', function(event, result) {
            $scope.nameCnclApplicationStatusLog = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
