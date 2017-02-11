'use strict';

angular.module('stepApp')
    .controller('InformationCorrectionDetailController', function ($scope, $rootScope, $stateParams, entity, InformationCorrection, InstEmployee, InstEmplDesignation) {
        $scope.informationCorrection = entity;
        $scope.load = function (id) {
            InformationCorrection.get({id: id}, function(result) {
                $scope.informationCorrection = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:informationCorrectionUpdate', function(event, result) {
            $scope.informationCorrection = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
