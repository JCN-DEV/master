'use strict';

angular.module('stepApp')
    .controller('InstFinancialInfoDetailController',
    ['$scope','$rootScope','$stateParams','entity','InstFinancialInfo',
    function ($scope, $rootScope, $stateParams, entity, InstFinancialInfo) {
        $scope.instFinancialInfo = entity;
        $scope.load = function (id) {
            InstFinancialInfo.get({id: id}, function(result) {
                $scope.instFinancialInfo = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:instFinancialInfoUpdate', function(event, result) {
            $scope.instFinancialInfo = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
