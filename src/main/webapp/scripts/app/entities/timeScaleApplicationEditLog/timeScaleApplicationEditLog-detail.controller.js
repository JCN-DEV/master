'use strict';

angular.module('stepApp')
    .controller('TimeScaleApplicationEditLogDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'TimeScaleApplicationEditLog', 'TimeScaleApplication',
    function ($scope, $rootScope, $stateParams, entity, TimeScaleApplicationEditLog, TimeScaleApplication) {
        $scope.timeScaleApplicationEditLog = entity;
        $scope.load = function (id) {
            TimeScaleApplicationEditLog.get({id: id}, function(result) {
                $scope.timeScaleApplicationEditLog = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:timeScaleApplicationEditLogUpdate', function(event, result) {
            $scope.timeScaleApplicationEditLog = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
