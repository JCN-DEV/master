'use strict';

angular.module('stepApp')
    .controller('DrugsDetailController', function ($scope, $rootScope, $stateParams, entity, Drugs) {
        $scope.drugs = entity;
        $scope.load = function (id) {
            Drugs.get({id: id}, function(result) {
                $scope.drugs = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:drugsUpdate', function(event, result) {
            $scope.drugs = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
