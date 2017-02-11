'use strict';

angular.module('stepApp')
    .controller('AssetDistributionDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'AssetDistribution', 'Employee', 'AssetRecord',
    function ($scope, $rootScope, $stateParams, entity, AssetDistribution, Employee, AssetRecord) {
        $scope.assetDistribution = entity;
        $scope.load = function (id) {
            AssetDistribution.get({id: id}, function(result) {
                $scope.assetDistribution = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:assetDistributionUpdate', function(event, result) {
            $scope.assetDistribution = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
