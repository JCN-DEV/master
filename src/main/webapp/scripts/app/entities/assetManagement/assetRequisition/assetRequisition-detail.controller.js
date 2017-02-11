'use strict';

angular.module('stepApp')
    .controller('AssetRequisitionDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'AssetRequisition', 'AssetTypeSetup', 'AssetCategorySetup', 'AssetRecord',
    function ($scope, $rootScope, $stateParams, entity, AssetRequisition, AssetTypeSetup, AssetCategorySetup, AssetRecord) {
        $scope.assetRequisition = entity;
        $scope.load = function (id) {
            AssetRequisition.get({id: id}, function(result) {
                $scope.assetRequisition = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:assetRequisitionUpdate', function(event, result) {
            $scope.assetRequisition = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
