'use strict';

angular.module('stepApp').controller('AssetAccuisitionSetupDialogController',
    ['$scope','$rootScope', '$stateParams', '$state', 'entity', 'AssetAccuisitionSetup',
        function($scope, $rootScope, $stateParams, $state, entity, AssetAccuisitionSetup) {

        $scope.assetAccuisitionSetup = entity;
        $scope.load = function(id) {
            AssetAccuisitionSetup.get({id : id}, function(result) {
                $scope.assetAccuisitionSetup = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('stepApp:assetAccuisitionSetupUpdate', result);
            //$modalInstance.close(result);
            $state.go('assetAccuisitionSetup');
        };

        $scope.save = function () {
            if ($scope.assetAccuisitionSetup.id != null) {
                AssetAccuisitionSetup.update($scope.assetAccuisitionSetup, onSaveFinished);
                $rootScope.setWarningMessage('stepApp.assetAccuisitionSetup.updated');
            } else {
                AssetAccuisitionSetup.save($scope.assetAccuisitionSetup, onSaveFinished);
                $rootScope.setSuccessMessage('stepApp.assetAccuisitionSetup.created');
            }
        };

        $scope.clear = function() {
            //$modalInstance.dismiss('cancel');
            $state.go('assetAccuisitionSetup');
        };
}]);
