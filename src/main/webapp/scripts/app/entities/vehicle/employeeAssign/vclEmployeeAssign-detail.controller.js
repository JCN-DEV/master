'use strict';

angular.module('stepApp')
    .controller('VclEmployeeAssignDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'VclEmployeeAssign', 'VclVehicle', 'Employee',
    function ($scope, $rootScope, $stateParams, entity, VclEmployeeAssign, VclVehicle, Employee) {
        $scope.vclEmployeeAssign = entity;
        $scope.load = function (id) {
            VclEmployeeAssign.get({id: id}, function(result) {
                $scope.vclEmployeeAssign = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:vclEmployeeAssignUpdate', function(event, result) {
            $scope.vclEmployeeAssign = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
