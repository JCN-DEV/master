'use strict';

angular.module('stepApp')
    .controller('AssetSupplierDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'AssetSupplier',
    function ($scope, $rootScope, $stateParams, entity, AssetSupplier) {
        $scope.assetSupplier = entity;
        $scope.load = function (id) {
            AssetSupplier.get({id: id}, function(result) {
                $scope.assetSupplier = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:assetSupplierUpdate', function(event, result) {
            $scope.assetSupplier = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
