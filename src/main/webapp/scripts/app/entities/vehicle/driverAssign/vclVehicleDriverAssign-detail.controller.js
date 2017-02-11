'use strict';

angular.module('stepApp')
    .controller('VclVehicleDriverAssignDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'VclVehicleDriverAssign', 'VclVehicle', 'VclDriver',
    function ($scope, $rootScope, $stateParams, entity, VclVehicleDriverAssign, VclVehicle, VclDriver) {
        $scope.vclVehicleDriverAssign = entity;
        $scope.load = function (id) {
            VclVehicleDriverAssign.get({id: id}, function(result) {
                $scope.vclVehicleDriverAssign = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:vclVehicleDriverAssignUpdate', function(event, result) {
            $scope.vclVehicleDriverAssign = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
