'use strict';

angular.module('stepApp')
    .controller('TimeScaleDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'TimeScale', 'PayScale',
    function ($scope, $rootScope, $stateParams, entity, TimeScale, PayScale) {
        $scope.timeScale = entity;
        $scope.load = function (id) {
            TimeScale.get({id: id}, function(result) {
                $scope.timeScale = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:timeScaleUpdate', function(event, result) {
            $scope.timeScale = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
