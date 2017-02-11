'use strict';

angular.module('stepApp')
    .controller('InstBuildingDetailController',
    ['$scope','$rootScope','$stateParams','entity','InstBuilding',
    function ($scope, $rootScope, $stateParams, entity, InstBuilding) {
        $scope.instBuilding = entity;
        $scope.load = function (id) {
            InstBuilding.get({id: id}, function(result) {
                $scope.instBuilding = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:instBuildingUpdate', function(event, result) {
            $scope.instBuilding = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
