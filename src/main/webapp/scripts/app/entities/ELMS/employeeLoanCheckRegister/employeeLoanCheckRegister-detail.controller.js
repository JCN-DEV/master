'use strict';

angular.module('stepApp')
    .controller('EmployeeLoanCheckRegisterDetailController', function ($scope, $rootScope, $stateParams, entity, EmployeeLoanCheckRegister, InstEmployee, EmployeeLoanRequisitionForm, EmployeeLoanBillRegister) {
        $scope.employeeLoanCheckRegister = entity;
        $scope.load = function (id) {
            EmployeeLoanCheckRegister.get({id: id}, function(result) {
                $scope.employeeLoanCheckRegister = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:employeeLoanCheckRegisterUpdate', function(event, result) {
            $scope.employeeLoanCheckRegister = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
