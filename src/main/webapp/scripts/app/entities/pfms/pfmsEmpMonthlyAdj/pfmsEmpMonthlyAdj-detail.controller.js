'use strict';

angular.module('stepApp')
    .controller('PfmsEmpMonthlyAdjDetailController', function ($scope, $rootScope, $stateParams, entity, PfmsEmpMonthlyAdj, HrEmployeeInfo) {
        $scope.pfmsEmpMonthlyAdj = entity;
        $scope.load = function (id) {
            PfmsEmpMonthlyAdj.get({id: id}, function(result) {
                $scope.pfmsEmpMonthlyAdj = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:pfmsEmpMonthlyAdjUpdate', function(event, result) {
            $scope.pfmsEmpMonthlyAdj = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
