'use strict';

angular.module('stepApp')
    .controller('PfmsUtmostGpfAppDetailController', function ($scope, $rootScope, $stateParams, entity, PfmsUtmostGpfApp, HrEmployeeInfo) {
        $scope.pfmsUtmostGpfApp = entity;
        $scope.load = function (id) {
            PfmsUtmostGpfApp.get({id: id}, function(result) {
                $scope.pfmsUtmostGpfApp = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:pfmsUtmostGpfAppUpdate', function(event, result) {
            $scope.pfmsUtmostGpfApp = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
