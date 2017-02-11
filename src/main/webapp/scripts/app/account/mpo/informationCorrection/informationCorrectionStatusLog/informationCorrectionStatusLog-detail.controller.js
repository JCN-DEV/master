'use strict';

angular.module('stepApp')
    .controller('InformationCorrectionStatusLogDetailController', function ($scope, $rootScope, $stateParams, entity, InformationCorrectionStatusLog, InformationCorrection) {
        $scope.informationCorrectionStatusLog = entity;
        $scope.load = function (id) {
            InformationCorrectionStatusLog.get({id: id}, function(result) {
                $scope.informationCorrectionStatusLog = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:informationCorrectionStatusLogUpdate', function(event, result) {
            $scope.informationCorrectionStatusLog = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
