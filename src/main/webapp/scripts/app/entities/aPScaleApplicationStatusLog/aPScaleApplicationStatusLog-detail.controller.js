'use strict';

angular.module('stepApp')
    .controller('APScaleApplicationStatusLogDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'APScaleApplicationStatusLog', 'TimeScaleApplication',
    function ($scope, $rootScope, $stateParams, entity, APScaleApplicationStatusLog, TimeScaleApplication) {
        $scope.aPScaleApplicationStatusLog = entity;
        $scope.load = function (id) {
            APScaleApplicationStatusLog.get({id: id}, function(result) {
                $scope.aPScaleApplicationStatusLog = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:aPScaleApplicationStatusLogUpdate', function(event, result) {
            $scope.aPScaleApplicationStatusLog = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
