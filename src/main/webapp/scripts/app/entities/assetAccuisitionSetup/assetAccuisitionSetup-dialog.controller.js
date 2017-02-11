'use strict';

angular.module('stepApp').controller('AssetAccuisitionSetupDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'AssetAccuisitionSetup',
        function($scope, $stateParams, $modalInstance, entity, AssetAccuisitionSetup) {

        $scope.assetAccuisitionSetup = entity;
        $scope.load = function(id) {
            AssetAccuisitionSetup.get({id : id}, function(result) {
                $scope.assetAccuisitionSetup = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('stepApp:assetAccuisitionSetupUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.assetAccuisitionSetup.id != null) {
                AssetAccuisitionSetup.update($scope.assetAccuisitionSetup, onSaveFinished);
            } else {
                AssetAccuisitionSetup.save($scope.assetAccuisitionSetup, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
