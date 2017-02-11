'use strict';

angular.module('stepApp').controller('FeePaymentCategorySetupDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'FeePaymentCategorySetup',
        function($scope, $stateParams, $modalInstance, entity, FeePaymentCategorySetup) {

        $scope.feePaymentCategorySetup = entity;
        $scope.load = function(id) {
            FeePaymentCategorySetup.get({id : id}, function(result) {
                $scope.feePaymentCategorySetup = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('stepApp:feePaymentCategorySetupUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.feePaymentCategorySetup.id != null) {
                FeePaymentCategorySetup.update($scope.feePaymentCategorySetup, onSaveFinished);
            } else {
                FeePaymentCategorySetup.save($scope.feePaymentCategorySetup, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
