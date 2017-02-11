'use strict';

angular.module('stepApp')
    .controller('TrainingBudgetInformationDetailController', function ($scope, $rootScope, $stateParams, entity, TrainingBudgetInformation, TrainingInitializationInfo) {
        $scope.trainingBudgetInformation = entity;
        $scope.load = function (id) {
            TrainingBudgetInformation.get({id: id}, function(result) {
                $scope.trainingBudgetInformation = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:trainingBudgetInformationUpdate', function(event, result) {
            $scope.trainingBudgetInformation = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
