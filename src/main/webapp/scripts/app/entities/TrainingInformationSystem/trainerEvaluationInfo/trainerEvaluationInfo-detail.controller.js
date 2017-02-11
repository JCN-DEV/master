'use strict';

angular.module('stepApp')
    .controller('TrainerEvaluationInfoDetailController', function ($scope, $rootScope, $stateParams, entity, TrainerEvaluationInfo, TrainingHeadSetup, TrainingInitializationInfo, TraineeInformation) {
        $scope.trainerEvaluationInfo = entity;
        $scope.load = function (id) {
            TrainerEvaluationInfo.get({id: id}, function(result) {
                $scope.trainerEvaluationInfo = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:trainerEvaluationInfoUpdate', function(event, result) {
            $scope.trainerEvaluationInfo = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
