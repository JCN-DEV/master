'use strict';

angular.module('stepApp')
    .controller('PayScaleDetailController',
    ['$scope','$rootScope','$stateParams','entity','PayScale','User',
    function ($scope, $rootScope, $stateParams, entity, PayScale, User) {
        $scope.payScale = entity;
        $scope.load = function (id) {
            PayScale.get({id: id}, function(result) {
                $scope.payScale = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:payScaleUpdate', function(event, result) {
            $scope.payScale = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
