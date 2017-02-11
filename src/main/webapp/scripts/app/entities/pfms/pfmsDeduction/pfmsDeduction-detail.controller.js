'use strict';

angular.module('stepApp')
    .controller('PfmsDeductionDetailController', function ($scope, $rootScope, $stateParams, entity, PfmsDeduction, HrEmployeeInfo) {
        $scope.pfmsDeduction = entity;
        $scope.load = function (id) {
            PfmsDeduction.get({id: id}, function(result) {
                $scope.pfmsDeduction = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:pfmsDeductionUpdate', function(event, result) {
            $scope.pfmsDeduction = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
