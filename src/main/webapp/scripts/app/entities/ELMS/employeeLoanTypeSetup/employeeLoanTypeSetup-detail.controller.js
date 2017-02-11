'use strict';

angular.module('stepApp')
    .controller('EmployeeLoanTypeSetupDetailController', function ($scope, $rootScope, $stateParams, entity, EmployeeLoanTypeSetup) {
        $scope.employeeLoanTypeSetup = entity;
        $scope.load = function (id) {
            EmployeeLoanTypeSetup.get({id: id}, function(result) {
                $scope.employeeLoanTypeSetup = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:employeeLoanTypeSetupUpdate', function(event, result) {
            $scope.employeeLoanTypeSetup = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
