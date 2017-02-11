'use strict';

angular.module('stepApp')
    .controller('AlmDutySideDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'AlmDutySide',
    function ($scope, $rootScope, $stateParams, entity, AlmDutySide) {
        $scope.almDutySide = entity;
        $scope.load = function (id) {
            AlmDutySide.get({id: id}, function(result) {
                $scope.almDutySide = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:almDutySideUpdate', function(event, result) {
            $scope.almDutySide = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
