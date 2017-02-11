'use strict';

angular.module('stepApp').controller('VclRequisitionDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'VclRequisition', 'User', 'VclVehicle', 'VclDriver',
        function($scope, $stateParams, $uibModalInstance, entity, VclRequisition, User, VclVehicle, VclDriver) {

        $scope.vclRequisition = entity;
        $scope.users = User.query();
        $scope.vclvehicles = VclVehicle.query();
        $scope.vcldrivers = VclDriver.query();
        $scope.load = function(id) {
            VclRequisition.get({id : id}, function(result) {
                $scope.vclRequisition = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:vclRequisitionUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.vclRequisition.id != null) {
                VclRequisition.update($scope.vclRequisition, onSaveSuccess, onSaveError);
            } else {
                VclRequisition.save($scope.vclRequisition, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
