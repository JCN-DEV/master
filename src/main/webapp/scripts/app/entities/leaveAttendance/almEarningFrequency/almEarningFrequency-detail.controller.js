'use strict';

angular.module('stepApp')
    .controller('AlmEarningFrequencyDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'AlmEarningFrequency',
    function ($scope, $rootScope, $stateParams, entity, AlmEarningFrequency) {
        $scope.almEarningFrequency = entity;
        $scope.load = function (id) {
            AlmEarningFrequency.get({id: id}, function(result) {
                $scope.almEarningFrequency = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:almEarningFrequencyUpdate', function(event, result) {
            $scope.almEarningFrequency = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
