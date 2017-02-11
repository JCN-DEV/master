'use strict';

angular.module('stepApp')
    .controller('PrlLocalitySetInfoDetailController', function ($scope, $rootScope, $stateParams, entity, PrlLocalitySetInfo, PrlLocalityInfo) {
        $scope.prlLocalitySetInfo = entity;
        $scope.load = function (id) {
            PrlLocalitySetInfo.get({id: id}, function(result) {
                $scope.prlLocalitySetInfo = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:prlLocalitySetInfoUpdate', function(event, result) {
            $scope.prlLocalitySetInfo = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
