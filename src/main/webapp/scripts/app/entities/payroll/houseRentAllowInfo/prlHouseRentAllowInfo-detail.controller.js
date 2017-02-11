'use strict';

angular.module('stepApp')
    .controller('PrlHouseRentAllowInfoDetailController', function ($scope, $rootScope, $stateParams, entity, PrlHouseRentAllowInfo, PrlLocalitySetInfo, HrGazetteSetup) {
        $scope.prlHouseRentAllowInfo = entity;
        $scope.load = function (id) {
            PrlHouseRentAllowInfo.get({id: id}, function(result) {
                $scope.prlHouseRentAllowInfo = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:prlHouseRentAllowInfoUpdate', function(event, result) {
            $scope.prlHouseRentAllowInfo = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
