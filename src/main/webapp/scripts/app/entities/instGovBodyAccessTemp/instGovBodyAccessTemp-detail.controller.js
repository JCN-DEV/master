'use strict';

angular.module('stepApp')
    .controller('InstGovBodyAccessTempDetailController', function ($scope, $rootScope, $stateParams, entity, InstGovBodyAccessTemp, Institute, User) {
        $scope.instGovBodyAccessTemp = entity;
        $scope.load = function (id) {
            InstGovBodyAccessTemp.get({id: id}, function(result) {
                $scope.instGovBodyAccessTemp = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:instGovBodyAccessTempUpdate', function(event, result) {
            $scope.instGovBodyAccessTemp = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
