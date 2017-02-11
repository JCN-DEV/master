'use strict';

angular.module('stepApp')
    .controller('DteJasperConfigurationDetailController', function ($scope, $rootScope, $stateParams, entity, DteJasperConfiguration, User) {
        $scope.dteJasperConfiguration = entity;
        $scope.load = function (id) {
            DteJasperConfiguration.get({id: id}, function(result) {
                $scope.dteJasperConfiguration = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:dteJasperConfigurationUpdate', function(event, result) {
            $scope.dteJasperConfiguration = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
