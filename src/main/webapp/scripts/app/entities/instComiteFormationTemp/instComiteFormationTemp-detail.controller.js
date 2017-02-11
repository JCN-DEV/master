'use strict';

angular.module('stepApp')
    .controller('InstComiteFormationTempDetailController', function ($scope, $rootScope, $stateParams, entity, InstComiteFormationTemp, InstMemShip) {
        $scope.instComiteFormationTemp = entity;
        $scope.load = function (id) {
            InstComiteFormationTemp.get({id: id}, function(result) {
                $scope.instComiteFormationTemp = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:instComiteFormationTempUpdate', function(event, result) {
            $scope.instComiteFormationTemp = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
