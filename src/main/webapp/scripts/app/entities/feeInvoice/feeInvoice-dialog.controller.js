'use strict';

angular.module('stepApp').controller('FeeInvoiceDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'FeeInvoice',
        function($scope, $stateParams, $modalInstance, entity, FeeInvoice) {

        $scope.feeInvoice = entity;
        $scope.load = function(id) {
            FeeInvoice.get({id : id}, function(result) {
                $scope.feeInvoice = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('stepApp:feeInvoiceUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.feeInvoice.id != null) {
                FeeInvoice.update($scope.feeInvoice, onSaveFinished);
            } else {
                FeeInvoice.save($scope.feeInvoice, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
