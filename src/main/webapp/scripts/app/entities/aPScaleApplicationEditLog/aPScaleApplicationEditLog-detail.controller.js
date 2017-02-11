'use strict';

angular.module('stepApp')
    .controller('APScaleApplicationEditLogDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'APScaleApplicationEditLog', 'TimeScaleApplication',
    function ($scope, $rootScope, $stateParams, entity, APScaleApplicationEditLog, TimeScaleApplication) {
        $scope.aPScaleApplicationEditLog = entity;
        $scope.load = function (id) {
            APScaleApplicationEditLog.get({id: id}, function(result) {
                $scope.aPScaleApplicationEditLog = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:aPScaleApplicationEditLogUpdate', function(event, result) {
            $scope.aPScaleApplicationEditLog = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
