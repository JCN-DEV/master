'use strict';

angular.module('stepApp')
    .controller('InstComiteFormationDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'InstComiteFormation', 'InstMemShip',
    function ($scope, $rootScope, $stateParams, entity, InstComiteFormation, InstMemShip) {
        $scope.instComiteFormation = entity;
        $scope.load = function (id) {
            InstComiteFormation.get({id: id}, function(result) {
                $scope.instComiteFormation = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:instComiteFormationUpdate', function(event, result) {
            $scope.instComiteFormation = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
