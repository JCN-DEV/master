'use strict';

angular.module('stepApp')
    .controller('InstGovBodyAccessDetailController', function ($scope, $rootScope, $stateParams, entity, InstGovBodyAccess, Institute, User) {
        $scope.instGovBodyAccess = entity;
        $scope.load = function (id) {
            InstGovBodyAccess.get({id: id}, function(result) {
                $scope.instGovBodyAccess = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:instGovBodyAccessUpdate', function(event, result) {
            $scope.instGovBodyAccess = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
