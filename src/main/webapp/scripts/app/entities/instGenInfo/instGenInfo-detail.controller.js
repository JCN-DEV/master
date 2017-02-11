'use strict';

angular.module('stepApp')
    .controller('InstGenInfoDetailController',
    ['$scope','$rootScope','$stateParams','entity','InstGenInfo','Institute','Upazila',
    function ($scope, $rootScope, $stateParams, entity, InstGenInfo, Institute, Upazila) {
        $scope.instGenInfo = entity;
        $scope.load = function (id) {
            InstGenInfo.get({id: id}, function(result) {
                $scope.instGenInfo = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:instGenInfoUpdate', function(event, result) {
            $scope.instGenInfo = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
