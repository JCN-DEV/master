'use strict';

angular.module('stepApp')
    .controller('FeeInvoiceDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'FeeInvoice',
     function ($scope, $rootScope, $stateParams, entity, FeeInvoice) {
        $scope.feeInvoice = entity;
        $scope.load = function (id) {
            FeeInvoice.get({id: id}, function(result) {
                $scope.feeInvoice = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:feeInvoiceUpdate', function(event, result) {
            $scope.feeInvoice = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
