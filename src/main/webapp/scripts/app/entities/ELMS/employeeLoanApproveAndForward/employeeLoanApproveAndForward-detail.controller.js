'use strict';

angular.module('stepApp')
    .controller('EmployeeLoanApproveAndForwardDetailController', function ($scope, $rootScope, $stateParams, entity, EmployeeLoanApproveAndForward, EmployeeLoanRequisitionForm, Authority) {
        $scope.employeeLoanApproveAndForward = entity;
        $scope.load = function (id) {
            EmployeeLoanApproveAndForward.get({id: id}, function(result) {
                $scope.employeeLoanApproveAndForward = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:employeeLoanApproveAndForwardUpdate', function(event, result) {
            $scope.employeeLoanApproveAndForward = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
