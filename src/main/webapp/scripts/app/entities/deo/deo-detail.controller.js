'use strict';

angular.module('stepApp')
    .controller('DeoDetailController', function ($scope, $rootScope, $stateParams, entity, Deo, User) {
        $scope.deo = entity;
        $scope.load = function (id) {
            Deo.get({id: id}, function(result) {
                $scope.deo = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:deoUpdate', function(event, result) {
            $scope.deo = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
