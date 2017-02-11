'use strict';

angular.module('stepApp')
    .controller('EmployeeLoanRulesSetupDetailController', function ($scope, $rootScope, $stateParams, entity, EmployeeLoanRulesSetup, EmployeeLoanTypeSetup) {
        $scope.employeeLoanRulesSetup = entity;
        $scope.load = function (id) {
            EmployeeLoanRulesSetup.get({id: id}, function(result) {
                $scope.employeeLoanRulesSetup = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:employeeLoanRulesSetupUpdate', function(event, result) {
            $scope.employeeLoanRulesSetup = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
