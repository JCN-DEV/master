'use strict';

angular.module('stepApp')
    .controller('AlmShiftSetupDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'AlmShiftSetup',
    function ($scope, $rootScope, $stateParams, entity, AlmShiftSetup) {
        $scope.almShiftSetup = entity;
        $scope.load = function (id) {
            AlmShiftSetup.get({id: id}, function(result) {
                $scope.almShiftSetup = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:almShiftSetupUpdate', function(event, result) {
            $scope.almShiftSetup = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
