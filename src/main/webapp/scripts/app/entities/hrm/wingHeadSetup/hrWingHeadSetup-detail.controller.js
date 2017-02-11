'use strict';

angular.module('stepApp')
    .controller('HrWingHeadSetupDetailController', function ($scope, $rootScope, $stateParams, entity, HrWingHeadSetup, HrWingSetup) {
        $scope.hrWingHeadSetup = entity;
        $scope.load = function (id) {
            HrWingHeadSetup.get({id: id}, function(result) {
                $scope.hrWingHeadSetup = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:hrWingHeadSetupUpdate', function(event, result) {
            $scope.hrWingHeadSetup = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
