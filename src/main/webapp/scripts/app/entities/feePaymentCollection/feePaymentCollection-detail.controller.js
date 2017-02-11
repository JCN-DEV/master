'use strict';

angular.module('stepApp')
    .controller('FeePaymentCollectionDetailController'
    ['$scope','$rootScope','$stateParams','entity','FeePaymentCollection','FeePaymentTypeSetup','FeePaymentCategorySetup',
     function ($scope,$rootScope,$stateParams,entity,FeePaymentCollection,FeePaymentTypeSetup, FeePaymentCategorySetup) {
        $scope.feePaymentCollection = entity;
        $scope.load = function (id) {
            FeePaymentCollection.get({id: id}, function(result) {
                $scope.feePaymentCollection = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:feePaymentCollectionUpdate',function(event, result) {
            $scope.feePaymentCollection = result;
            $scope.$on('$destroy', unsubscribe);
        });
    }]);
