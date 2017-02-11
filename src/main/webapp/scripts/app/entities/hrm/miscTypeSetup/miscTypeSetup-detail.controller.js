'use strict';

angular.module('stepApp')
    .controller('MiscTypeSetupDetailController',
     ['$scope', '$rootScope', '$stateParams', 'entity', 'MiscTypeSetup',
     function ($scope, $rootScope, $stateParams, entity, MiscTypeSetup) {
        $scope.miscTypeSetup = entity;
        $scope.load = function (id) {
            MiscTypeSetup.get({id: id}, function(result) {
                $scope.miscTypeSetup = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:miscTypeSetupUpdate', function(event, result) {
            $scope.miscTypeSetup = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
