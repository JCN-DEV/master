'use strict';

angular.module('stepApp')
    .controller('VclRequisitionDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'VclRequisition', 'User', 'VclVehicle', 'VclDriver',
    function ($scope, $rootScope, $stateParams, entity, VclRequisition, User, VclVehicle, VclDriver) {
        $scope.vclRequisition = entity;
        $scope.load = function (id) {
            VclRequisition.get({id: id}, function(result) {
                $scope.vclRequisition = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:vclRequisitionUpdate', function(event, result) {
            $scope.vclRequisition = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
