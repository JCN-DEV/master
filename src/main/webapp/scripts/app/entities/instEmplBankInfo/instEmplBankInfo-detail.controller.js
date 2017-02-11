'use strict';

angular.module('stepApp')
    .controller('InstEmplBankInfoDetailController',
    ['$scope','$rootScope','$stateParams','entity','InstEmplBankInfo','InstEmployee',
     function ($scope, $rootScope, $stateParams, entity, InstEmplBankInfo, InstEmployee) {
        $scope.instEmplBankInfo = entity;
        $scope.load = function (id) {
            InstEmplBankInfo.get({id: id}, function(result) {
                $scope.instEmplBankInfo = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:instEmplBankInfoUpdate', function(event, result) {
            $scope.instEmplBankInfo = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
