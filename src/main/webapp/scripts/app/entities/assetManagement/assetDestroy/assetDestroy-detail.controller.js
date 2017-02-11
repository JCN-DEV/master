'use strict';

angular.module('stepApp')
    .controller('AssetDestroyDetailController',
    ['$scope', '$rootScope', '$stateParams','entity', 'AssetDestroy', 'AssetDistribution', 'AssetRecord',
    function ($scope, $rootScope, $stateParams, entity, AssetDestroy, AssetDistribution, AssetRecord) {
        $scope.assetDestroy = entity;
        $scope.load = function (id) {
            AssetDestroy.get({id: id}, function(result) {
                $scope.assetDestroy = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:assetDestroyUpdate', function(event, result) {
            $scope.assetDestroy = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
