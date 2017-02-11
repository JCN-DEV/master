'use strict';

angular.module('stepApp')
    .controller('BankAssignDetailController', function ($scope, $rootScope, $stateParams, entity, BankAssign, User, BankSetup, Upazila) {
        $scope.bankAssign = entity;
        $scope.load = function (id) {
            BankAssign.get({id: id}, function(result) {
                $scope.bankAssign = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:bankAssignUpdate', function(event, result) {
            $scope.bankAssign = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
