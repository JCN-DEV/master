'use strict';

angular.module('stepApp')
    .controller('DeoHistLogDetailController', function ($scope, $rootScope, $stateParams, entity, DeoHistLog, Deo, District) {
        $scope.deoHistLog = entity;
        $scope.load = function (id) {
            DeoHistLog.get({id: id}, function(result) {
                $scope.deoHistLog = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:deoHistLogUpdate', function(event, result) {
            $scope.deoHistLog = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
