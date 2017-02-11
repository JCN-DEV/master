'use strict';

angular.module('stepApp')
    .controller('BankBranchDetailController', function ($scope, $rootScope, $stateParams, entity, BankBranch, BankSetup, Upazila) {
        $scope.bankBranch = entity;
        $scope.load = function (id) {
            BankBranch.get({id: id}, function(result) {
                $scope.bankBranch = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:bankBranchUpdate', function(event, result) {
            $scope.bankBranch = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
