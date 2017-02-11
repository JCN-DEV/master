'use strict';

angular.module('stepApp')
    .controller('FeePaymentTypeSetupDetailController',
    ['$scope','$rootScope','$stateParams','entity','FeePaymentTypeSetup',

    function ($scope, $rootScope, $stateParams, entity, FeePaymentTypeSetup) {
        $scope.feePaymentTypeSetup = entity;
        $scope.load = function (id) {
            FeePaymentTypeSetup.get({id: id}, function(result) {
                $scope.feePaymentTypeSetup = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:feePaymentTypeSetupUpdate', function(event, result) {
            $scope.feePaymentTypeSetup = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
