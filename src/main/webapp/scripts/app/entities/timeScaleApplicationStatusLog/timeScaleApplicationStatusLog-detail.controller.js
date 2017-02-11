'use strict';

angular.module('stepApp')
    .controller('TimeScaleApplicationStatusLogDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'TimeScaleApplicationStatusLog', 'TimeScaleApplication',
    function ($scope, $rootScope, $stateParams, entity, TimeScaleApplicationStatusLog, TimeScaleApplication) {
        $scope.timeScaleApplicationStatusLog = entity;
        $scope.load = function (id) {
            TimeScaleApplicationStatusLog.get({id: id}, function(result) {
                $scope.timeScaleApplicationStatusLog = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:timeScaleApplicationStatusLogUpdate', function(event, result) {
            $scope.timeScaleApplicationStatusLog = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
