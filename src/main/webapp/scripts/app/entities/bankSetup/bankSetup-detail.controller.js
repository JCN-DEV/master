'use strict';

angular.module('stepApp')
    .controller('BankSetupDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'BankSetup', 'Upazila',
     function ($scope, $rootScope, $stateParams, entity, BankSetup, Upazila) {
        $scope.bankSetup = entity;
        $scope.load = function (id) {
            BankSetup.get({id: id}, function(result) {
                $scope.bankSetup = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:bankSetupUpdate', function(event, result) {
            $scope.bankSetup = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
