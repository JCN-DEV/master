'use strict';

angular.module('stepApp')
    .controller('DlSourceSetUpDetailController', function ($scope, $rootScope, $stateParams, entity, DlSourceSetUp) {
        $scope.dlSourceSetUp = entity;
        $scope.load = function (id) {
            DlSourceSetUp.get({id: id}, function(result) {
                $scope.dlSourceSetUp = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:dlSourceSetUpUpdate', function(event, result) {
            $scope.dlSourceSetUp = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
