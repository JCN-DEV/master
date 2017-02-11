'use strict';

angular.module('stepApp')
    .controller('BEdApplicationEditLogDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'BEdApplicationEditLog', 'BEdApplication',
     function ($scope, $rootScope, $stateParams, entity, BEdApplicationEditLog, BEdApplication) {
        $scope.bEdApplicationEditLog = entity;
        $scope.load = function (id) {
            BEdApplicationEditLog.get({id: id}, function(result) {
                $scope.bEdApplicationEditLog = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:bEdApplicationEditLogUpdate', function(event, result) {
            $scope.bEdApplicationEditLog = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
