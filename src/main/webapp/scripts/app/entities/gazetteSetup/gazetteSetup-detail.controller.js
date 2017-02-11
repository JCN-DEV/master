'use strict';

angular.module('stepApp')
    .controller('GazetteSetupDetailController',
    ['$scope','$rootScope','$stateParams','entity','GazetteSetup',
     function ($scope, $rootScope, $stateParams, entity, GazetteSetup) {
        $scope.gazetteSetup = entity;
        $scope.load = function (id) {
            GazetteSetup.get({id: id}, function(result) {
                $scope.gazetteSetup = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:gazetteSetupUpdate', function(event, result) {
            $scope.gazetteSetup = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
