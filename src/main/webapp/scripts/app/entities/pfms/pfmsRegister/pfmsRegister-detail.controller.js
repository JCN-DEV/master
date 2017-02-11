'use strict';

angular.module('stepApp')
    .controller('PfmsRegisterDetailController', function ($scope, $rootScope, $stateParams, entity, PfmsRegister, HrEmployeeInfo) {
        $scope.pfmsRegister = entity;
        $scope.load = function (id) {
            PfmsRegister.get({id: id}, function(result) {
                $scope.pfmsRegister = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:pfmsRegisterUpdate', function(event, result) {
            $scope.pfmsRegister = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
