'use strict';

angular.module('stepApp')
    .controller('PersonalPayDetailController', function ($scope, $rootScope, $stateParams, entity, PersonalPay, PayScale, User) {
        $scope.personalPay = entity;
        $scope.load = function (id) {
            PersonalPay.get({id: id}, function(result) {
                $scope.personalPay = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:personalPayUpdate', function(event, result) {
            $scope.personalPay = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
