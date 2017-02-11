'use strict';

angular.module('stepApp')
    .controller('InstMemShipDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'InstMemShip',
    function ($scope, $rootScope, $stateParams, entity, InstMemShip) {
        $scope.instMemShip = entity;
        $scope.load = function (id) {
            InstMemShip.get({id: id}, function(result) {
                $scope.instMemShip = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:instMemShipUpdate', function(event, result) {
            $scope.instMemShip = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
