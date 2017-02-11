'use strict';

angular.module('stepApp')
    .controller('AssetAccuisitionSetupDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'AssetAccuisitionSetup',
    function ($scope, $rootScope, $stateParams, entity, AssetAccuisitionSetup) {
        $scope.assetAccuisitionSetup = entity;
        $scope.load = function (id) {
            AssetAccuisitionSetup.get({id: id}, function(result) {
                $scope.assetAccuisitionSetup = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:assetAccuisitionSetupUpdate', function(event, result) {
            $scope.assetAccuisitionSetup = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
