'use strict';

angular.module('stepApp')
    .controller('TrainingSummaryDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'TrainingSummary', 'User',
    function ($scope, $rootScope, $stateParams, entity, TrainingSummary, User) {
        $scope.trainingSummary = entity;
        $scope.load = function (id) {
            TrainingSummary.get({id: id}, function(result) {
                $scope.trainingSummary = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:trainingSummaryUpdate', function(event, result) {
            $scope.trainingSummary = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
