'use strict';

angular.module('stepApp')
    .controller('PfmsLoanScheduleDetailController', function ($scope, $rootScope, $stateParams, entity, PfmsLoanSchedule, HrEmployeeInfo, PfmsLoanApplication) {
        $scope.pfmsLoanSchedule = entity;
        $scope.load = function (id) {
            PfmsLoanSchedule.get({id: id}, function(result) {
                $scope.pfmsLoanSchedule = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:pfmsLoanScheduleUpdate', function(event, result) {
            $scope.pfmsLoanSchedule = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
