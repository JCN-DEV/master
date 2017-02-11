'use strict';

angular.module('stepApp')
    .controller('AssetCategorySetupDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'AssetCategorySetup', 'AssetTypeSetup',
    function ($scope, $rootScope, $stateParams, entity, AssetCategorySetup, AssetTypeSetup) {
        $scope.assetCategorySetup = entity;
        $scope.load = function (id) {
            AssetCategorySetup.get({id: id}, function(result) {
                $scope.assetCategorySetup = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:assetCategorySetupUpdate', function(event, result) {
            $scope.assetCategorySetup = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
