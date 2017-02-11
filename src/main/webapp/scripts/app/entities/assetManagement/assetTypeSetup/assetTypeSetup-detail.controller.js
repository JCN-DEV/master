'use strict';

angular.module('stepApp')
    .controller('AssetTypeSetupDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'AssetTypeSetup',
    function ($scope, $rootScope, $stateParams, entity, AssetTypeSetup) {
        $scope.assetTypeSetup = entity;
        $scope.load = function (id) {
            AssetTypeSetup.get({id: id}, function(result) {
                console.log("bbbbbb");
                $scope.assetTypeSetup = result;

                console.log("");
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:assetTypeSetupUpdate', function(event, result) {
            $scope.assetTypeSetup = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
