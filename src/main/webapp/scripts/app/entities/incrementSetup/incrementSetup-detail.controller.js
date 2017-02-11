'use strict';

angular.module('stepApp')
    .controller('IncrementSetupDetailController',
    ['$scope','$rootScope','$stateParams','entity','IncrementSetup','PayScale',
    function ($scope, $rootScope, $stateParams, entity, IncrementSetup, PayScale) {
        $scope.incrementSetup = entity;
        $scope.load = function (id) {
            IncrementSetup.get({id: id}, function(result) {
                $scope.incrementSetup = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:incrementSetupUpdate', function(event, result) {
            $scope.incrementSetup = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
