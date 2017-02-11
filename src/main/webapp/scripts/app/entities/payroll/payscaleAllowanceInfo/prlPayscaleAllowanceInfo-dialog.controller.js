'use strict';

angular.module('stepApp').controller('PrlPayscaleAllowanceInfoDialogController',
    ['$scope', '$stateParams', '$state', 'entity', 'PrlPayscaleAllowanceInfo', 'PrlPayscaleInfo', 'PrlAllowDeductInfo',
        function($scope, $stateParams, $state, entity, PrlPayscaleAllowanceInfo, PrlPayscaleInfo, PrlAllowDeductInfo) {

        $scope.prlPayscaleAllowanceInfo = entity;
        $scope.prlpayscaleinfos = PrlPayscaleInfo.query();
        $scope.prlallowdeductinfos = PrlAllowDeductInfo.query();
        $scope.load = function(id) {
            PrlPayscaleAllowanceInfo.get({id : id}, function(result) {
                $scope.prlPayscaleAllowanceInfo = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:prlPayscaleAllowanceInfoUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.prlPayscaleAllowanceInfo.id != null) {
                PrlPayscaleAllowanceInfo.update($scope.prlPayscaleAllowanceInfo, onSaveSuccess, onSaveError);
            } else {
                PrlPayscaleAllowanceInfo.save($scope.prlPayscaleAllowanceInfo, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
