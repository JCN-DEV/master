'use strict';

angular.module('stepApp').controller('DlContTypeSetDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'DlContTypeSet',
        function($scope, $stateParams, $modalInstance, entity, DlContTypeSet) {

        $scope.dlContTypeSet = entity;
        $scope.load = function(id) {
            DlContTypeSet.get({id : id}, function(result) {
                $scope.dlContTypeSet = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:dlContTypeSetUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.dlContTypeSet.id != null) {
                DlContTypeSet.update($scope.dlContTypeSet, onSaveSuccess, onSaveError);
            } else {
                DlContTypeSet.save($scope.dlContTypeSet, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
