'use strict';

angular.module('stepApp').controller('DesignationDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Designation',
        function($scope, $stateParams, $modalInstance, entity, Designation) {

        $scope.designation = entity;
        $scope.load = function(id) {
            Designation.get({id : id}, function(result) {
                $scope.designation = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:designationUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.designation.id != null) {
                Designation.update($scope.designation, onSaveSuccess, onSaveError);
            } else {
                Designation.save($scope.designation, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
