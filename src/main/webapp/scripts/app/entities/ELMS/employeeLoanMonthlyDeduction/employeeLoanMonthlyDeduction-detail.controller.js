'use strict';

angular.module('stepApp')
    .controller('EmployeeLoanMonthlyDeductionDetailController', function ($scope, $rootScope, $stateParams, entity, EmployeeLoanMonthlyDeduction, InstEmployee, EmployeeLoanRequisitionForm) {
        $scope.employeeLoanMonthlyDeduction = entity;
        $scope.load = function (id) {
            EmployeeLoanMonthlyDeduction.get({id: id}, function(result) {
                $scope.employeeLoanMonthlyDeduction = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:employeeLoanMonthlyDeductionUpdate', function(event, result) {
            $scope.employeeLoanMonthlyDeduction = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
