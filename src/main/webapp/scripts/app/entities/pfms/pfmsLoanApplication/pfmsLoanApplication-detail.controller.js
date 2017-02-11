'use strict';

angular.module('stepApp')
    .controller('PfmsLoanApplicationDetailController', function ($scope, $rootScope, $stateParams, entity, PfmsLoanApplication, HrEmployeeInfo) {
        $scope.pfmsLoanApplication = entity;
        $scope.load = function (id) {
            PfmsLoanApplication.get({id: id}, function(result) {
                $scope.pfmsLoanApplication = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:pfmsLoanApplicationUpdate', function(event, result) {
            $scope.pfmsLoanApplication = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
