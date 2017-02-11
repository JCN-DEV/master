'use strict';

angular.module('stepApp')
    .controller('AssetRecordDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'AssetRecord', 'AssetCategorySetup', 'AssetTypeSetup', 'AssetSupplier',
    function ($scope, $rootScope, $stateParams, entity, AssetRecord, AssetCategorySetup, AssetTypeSetup, AssetSupplier) {
        $scope.assetRecord = entity;
        $scope.load = function (id) {
            AssetRecord.get({id: id}, function(result) {
                $scope.assetRecord = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:assetRecordUpdate', function(event, result) {
            $scope.assetRecord = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
