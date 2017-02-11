'use strict';

angular.module('stepApp').controller('AssetRequisitionDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'AssetRequisition', 'AssetTypeSetup', 'AssetCategorySetup', 'AssetRecord',
        function($scope, $stateParams, $modalInstance, entity, AssetRequisition, AssetTypeSetup, AssetCategorySetup, AssetRecord) {

        $scope.assetRequisition = entity;
        $scope.assettypesetups = AssetTypeSetup.query();
        $scope.assetcategorysetups = AssetCategorySetup.query();
        $scope.assetrecords = AssetRecord.query();
        $scope.load = function(id) {
            AssetRequisition.get({id : id}, function(result) {
                $scope.assetRequisition = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('stepApp:assetRequisitionUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.assetRequisition.id != null) {
                AssetRequisition.update($scope.assetRequisition, onSaveFinished);
            } else {
                AssetRequisition.save($scope.assetRequisition, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
