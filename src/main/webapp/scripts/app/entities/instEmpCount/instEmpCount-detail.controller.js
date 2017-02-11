'use strict';

angular.module('stepApp')
    .controller('InstEmpCountDetailController',
    ['$scope','$rootScope','$stateParams','entity','InstEmpCount','Institute',
    function ($scope, $rootScope, $stateParams, entity, InstEmpCount, Institute) {
        $scope.instEmpCount = entity;
        $scope.load = function (id) {
            InstEmpCount.get({id: id}, function(result) {
                $scope.instEmpCount = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:instEmpCountUpdate', function(event, result) {
            $scope.instEmpCount = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
