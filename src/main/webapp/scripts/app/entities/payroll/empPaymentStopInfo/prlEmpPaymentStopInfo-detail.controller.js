'use strict';

angular.module('stepApp')
    .controller('PrlEmpPaymentStopInfoDetailController', function ($scope, $rootScope, $stateParams, entity, PrlEmpPaymentStopInfo, HrEmployeeInfo) {
        $scope.prlEmpPaymentStopInfo = entity;
        $scope.load = function (id) {
            PrlEmpPaymentStopInfo.get({id: id}, function(result) {
                $scope.prlEmpPaymentStopInfo = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:prlEmpPaymentStopInfoUpdate', function(event, result) {
            $scope.prlEmpPaymentStopInfo = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
