'use strict';

angular.module('stepApp')
    .controller('InformationCorrectionEditLogDetailController', function ($scope, $rootScope, $stateParams, entity, InformationCorrectionEditLog, InformationCorrection) {
        $scope.informationCorrectionEditLog = entity;
        $scope.load = function (id) {
            InformationCorrectionEditLog.get({id: id}, function(result) {
                $scope.informationCorrectionEditLog = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:informationCorrectionEditLogUpdate', function(event, result) {
            $scope.informationCorrectionEditLog = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
