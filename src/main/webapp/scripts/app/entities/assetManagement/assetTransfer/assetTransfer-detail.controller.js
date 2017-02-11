'use strict';

angular.module('stepApp')
    .controller('AssetTransferDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'AssetTransfer', 'Employee', 'AssetRecord',
    function ($scope, $rootScope, $stateParams, entity, AssetTransfer, Employee, AssetRecord) {
        $scope.assetTransfer = entity;
        $scope.load = function (id) {
            AssetTransfer.get({id: id}, function(result) {
                $scope.assetTransfer = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:assetTransferUpdate', function(event, result) {
            $scope.assetTransfer = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
