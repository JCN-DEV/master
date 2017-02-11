'use strict';

angular.module('stepApp')
    .controller('HrWingSetupDetailController', function ($scope, $rootScope, $stateParams, entity, HrWingSetup, HrEmployeeInfo) {
        $scope.hrWingSetup = entity;
        $scope.load = function (id) {
            HrWingSetup.get({id: id}, function(result) {
                $scope.hrWingSetup = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:hrWingSetupUpdate', function(event, result) {
            $scope.hrWingSetup = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
