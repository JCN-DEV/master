'use strict';

angular.module('stepApp').controller('FeePaymentCollectionDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'FeePaymentCollection', 'FeePaymentTypeSetup', 'FeePaymentCategorySetup',
        function($scope, $stateParams, $modalInstance, entity, FeePaymentCollection, FeePaymentTypeSetup, FeePaymentCategorySetup) {

        $scope.feePaymentCollection = entity;
        $scope.feepaymenttypesetups = FeePaymentTypeSetup.query();
        $scope.feepaymentcategorysetups = FeePaymentCategorySetup.query();
        $scope.load = function(id) {
            FeePaymentCollection.get({id : id}, function(result) {
                $scope.feePaymentCollection = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('stepApp:feePaymentCollectionUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.feePaymentCollection.id != null) {
                FeePaymentCollection.update($scope.feePaymentCollection, onSaveFinished);
            } else {
                FeePaymentCollection.save($scope.feePaymentCollection, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
