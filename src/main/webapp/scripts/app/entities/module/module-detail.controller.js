'use strict';

angular.module('stepApp')
    .controller('ModuleDetailController',
    ['$scope','$rootScope','$stateParams','entity','Module',
    function ($scope, $rootScope, $stateParams, entity, Module) {
        $scope.module = entity;
        $scope.load = function (id) {
            Module.get({id: id}, function(result) {
                $scope.module = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:moduleUpdate', function(event, result) {
            $scope.module = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
