'use strict';

angular.module('stepApp').controller('DlFileTypeDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'DlFileType',
        function($scope, $stateParams, $modalInstance, entity, DlFileType) {

        $scope.dlFileType = entity;
        $scope.load = function(id) {
            DlFileType.get({id : id}, function(result) {
                $scope.dlFileType = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:dlFileTypeUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.dlFileType.id != null) {
                DlFileType.update($scope.dlFileType, onSaveSuccess, onSaveError);
            } else {
                DlFileType.save($scope.dlFileType, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
