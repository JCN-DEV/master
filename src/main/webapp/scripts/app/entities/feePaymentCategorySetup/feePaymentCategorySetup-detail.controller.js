'use strict';

angular.module('stepApp')
    .controller('FeePaymentCategorySetupDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'FeePaymentCategorySetup',
    function ($scope, $rootScope, $stateParams, entity, FeePaymentCategorySetup) {
        $scope.feePaymentCategorySetup = entity;
        $scope.load = function (id) {
            FeePaymentCategorySetup.get({id: id}, function(result) {
                $scope.feePaymentCategorySetup = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:feePaymentCategorySetupUpdate', function(event, result) {
            $scope.feePaymentCategorySetup = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
