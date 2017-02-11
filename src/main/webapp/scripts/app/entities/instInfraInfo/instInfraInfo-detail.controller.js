'use strict';

angular.module('stepApp')
    .controller('InstInfraInfoDetailController',
    ['$scope','$rootScope','$stateParams','entity','InstInfraInfo','Institute','InstBuilding','InstLand',
    function ($scope, $rootScope, $stateParams, entity, InstInfraInfo, Institute, InstBuilding, InstLand) {
        $scope.instInfraInfo = entity;
        $scope.load = function (id) {
            InstInfraInfo.get({id: id}, function(result) {
                $scope.instInfraInfo = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:instInfraInfoUpdate', function(event, result) {
            $scope.instInfraInfo = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
