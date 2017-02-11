'use strict';

angular.module('stepApp')
    .controller('TrainingHeadSetupDetailController',
        ['$scope', '$rootScope', '$stateParams', 'entity', 'TrainingHeadSetup',
        function ($scope, $rootScope, $stateParams, entity, TrainingHeadSetup) {
        $scope.trainingHeadSetup = entity;
        $scope.load = function (id) {
            TrainingHeadSetup.get({id: id}, function(result) {
                $scope.trainingHeadSetup = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:trainingHeadSetupUpdate', function(event, result) {
            $scope.trainingHeadSetup = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
