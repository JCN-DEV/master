'use strict';

angular.module('stepApp')
    .controller('VclVehicleDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'VclVehicle', 'VclDriver',
    function ($scope, $rootScope, $stateParams, entity, VclVehicle, VclDriver) {
        $scope.vclVehicle = entity;
        $scope.load = function (id) {
            VclVehicle.get({id: id}, function(result) {
                $scope.vclVehicle = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:vclVehicleUpdate', function(event, result) {
            $scope.vclVehicle = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
