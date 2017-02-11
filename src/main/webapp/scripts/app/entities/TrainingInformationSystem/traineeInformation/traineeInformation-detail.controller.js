'use strict';

angular.module('stepApp')
    .controller('TraineeInformationDetailController', function ($scope, $rootScope, $stateParams, entity, TraineeInformation, TrainingHeadSetup, HrEmployeeInfo) {
        $scope.traineeInformation = entity;
        $scope.load = function (id) {
            TraineeInformation.get({id: id}, function(result) {
                $scope.traineeInformation = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:traineeInformationUpdate', function(event, result) {
            $scope.traineeInformation = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
