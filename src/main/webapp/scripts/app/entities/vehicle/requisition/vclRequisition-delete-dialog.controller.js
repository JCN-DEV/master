'use strict';

angular.module('stepApp')
	.controller('VclRequisitionDeleteController',
    ['$scope', '$modalInstance', 'entity', 'VclRequisition',
    function($scope, $modalInstance, entity, VclRequisition) {

        $scope.vclRequisition = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:vclRequisitionUpdate', result);
            $scope.isSaving = false;
            $modalInstance.close(true);
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
            $modalInstance.dismiss('cancel');
        };

        $scope.confirmDelete = function (id)
        {
            VclRequisition.get({id : id}, function(result)
            {
                $scope.vclRequisition = result;
                $scope.vclRequisition.activeStatus = false;
                VclRequisition.update($scope.vclRequisition, onSaveSuccess, onSaveError);
            });

        };

    }]);
