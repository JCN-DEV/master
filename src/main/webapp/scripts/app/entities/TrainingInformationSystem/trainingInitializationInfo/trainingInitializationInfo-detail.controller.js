'use strict';

angular.module('stepApp')
    .controller('TrainingInitializationInfoDetailController', function ($scope, $rootScope, $stateParams, entity, TrainingInitializationInfo, TrainingHeadSetup, TrainingRequisitionForm) {
        $scope.trainingInitializationInfo = entity;
        $scope.load = function (id) {
            TrainingInitializationInfo.get({id: id}, function(result) {
                $scope.trainingInitializationInfo = result;
            });
        };

        console.log($scope.trainingInitializationInfo);

        var unsubscribe = $rootScope.$on('stepApp:trainingInitializationInfoUpdate', function(event, result) {
            $scope.trainingInitializationInfo = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
