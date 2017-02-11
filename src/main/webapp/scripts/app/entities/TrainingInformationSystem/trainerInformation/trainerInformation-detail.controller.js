'use strict';

angular.module('stepApp')
    .controller('TrainerInformationDetailController', function ($scope, $rootScope, $stateParams, entity, TrainerInformation, TrainingInitializationInfo, HrEmployeeInfo) {
        $scope.trainerInformation = entity;
        $scope.load = function (id) {
            TrainerInformation.get({id: id}, function(result) {
                $scope.trainerInformation = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:trainerInformationUpdate', function(event, result) {
            $scope.trainerInformation = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
