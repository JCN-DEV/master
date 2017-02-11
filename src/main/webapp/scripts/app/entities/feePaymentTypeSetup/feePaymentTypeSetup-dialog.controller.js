'use strict';

angular.module('stepApp').controller('FeePaymentTypeSetupDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'FeePaymentTypeSetup',
        function($scope, $stateParams, $modalInstance, entity, FeePaymentTypeSetup) {

        $scope.feePaymentTypeSetup = entity;
        $scope.load = function(id) {
            FeePaymentTypeSetup.get({id : id}, function(result) {
                $scope.feePaymentTypeSetup = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('stepApp:feePaymentTypeSetupUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.feePaymentTypeSetup.id != null) {
                FeePaymentTypeSetup.update($scope.feePaymentTypeSetup, onSaveFinished);
            } else {
                FeePaymentTypeSetup.save($scope.feePaymentTypeSetup, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
