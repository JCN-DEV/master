'use strict';

angular.module('stepApp')
    .controller('AlmWorkingUnitDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'AlmWorkingUnit', 'AlmShiftSetup',
    function ($scope, $rootScope, $stateParams, entity, AlmWorkingUnit, AlmShiftSetup) {
        $scope.almWorkingUnit = entity;
        $scope.load = function (id) {
            AlmWorkingUnit.get({id: id}, function(result) {
                $scope.almWorkingUnit = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:almWorkingUnitUpdate', function(event, result) {
            $scope.almWorkingUnit = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
