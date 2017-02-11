'use strict';

angular.module('stepApp')
    .controller('PfmsEmpAdjustmentDetailController', function ($scope, $rootScope, $stateParams, entity, PfmsEmpAdjustment, HrEmployeeInfo) {
        $scope.pfmsEmpAdjustment = entity;
        $scope.load = function (id) {
            PfmsEmpAdjustment.get({id: id}, function(result) {
                $scope.pfmsEmpAdjustment = result;
            });
        };
        console.log($scope.pfmsEmpAdjustment);
        var unsubscribe = $rootScope.$on('stepApp:pfmsEmpAdjustmentUpdate', function(event, result) {
            $scope.pfmsEmpAdjustment = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
