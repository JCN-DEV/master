'use strict';

angular.module('stepApp')
    .controller('TraineeEvaluationInfoDetailController', function ($scope, $rootScope, $stateParams, entity, TraineeEvaluationInfo, TrainingHeadSetup, HrEmployeeInfo, TrainingInitializationInfo) {
        $scope.traineeEvaluationInfo = entity;
        $scope.load = function (id) {
            TraineeEvaluationInfo.get({id: id}, function(result) {
                $scope.traineeEvaluationInfo = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:traineeEvaluationInfoUpdate', function(event, result) {
            $scope.traineeEvaluationInfo = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
