'use strict';

angular.module('stepApp')
    .controller('EmployeeLoanBillRegisterDetailController', function ($scope, $rootScope, $stateParams, entity, EmployeeLoanBillRegister, InstEmployee, EmployeeLoanRequisitionForm) {
        $scope.employeeLoanBillRegister = entity;
        $scope.load = function (id) {
            EmployeeLoanBillRegister.get({id: id}, function(result) {
                $scope.employeeLoanBillRegister = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:employeeLoanBillRegisterUpdate', function(event, result) {
            $scope.employeeLoanBillRegister = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
