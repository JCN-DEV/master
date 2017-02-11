'use strict';

angular.module('stepApp').controller('AssetSupplierDialogController',
    ['$scope', '$rootScope','$state', '$stateParams', 'entity', 'AssetSupplier',
        function($scope, $rootScope, $state, $stateParams, entity, AssetSupplier) {

        $scope.assetSupplier = entity;
        $scope.load = function(id) {
            AssetSupplier.get({id : id}, function(result) {
                $scope.assetSupplier = result;
                $scope.assetSupplier.contactNo = parseInt(result.contactNo);
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('stepApp:assetSupplierUpdate', result);

            $state.go('assetSupplier', null,{ reload: true }),
                function (){
                    $state.go('assetSupplier');
                };

        };

        $scope.save = function () {
            if ($scope.assetSupplier.id != null) {
                AssetSupplier.update($scope.assetSupplier, onSaveFinished);
                $rootScope.setWarningMessage('stepApp.assetSupplier.updated');
            } else {
                AssetSupplier.save($scope.assetSupplier, onSaveFinished);
                $rootScope.setSuccessMessage('stepApp.assetSupplier.created');
            }
        };

        $scope.clear = function() {

        };
}]);
