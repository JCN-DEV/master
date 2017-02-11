'use strict';

angular.module('stepApp').controller('BEdApplicationDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'BEdApplication', 'InstEmployee',
        function($scope, $stateParams, $modalInstance, entity, BEdApplication, InstEmployee) {

        $scope.bEdApplication = entity;
        $scope.instemployees = InstEmployee.query();
        $scope.load = function(id) {
            BEdApplication.get({id : id}, function(result) {
                $scope.bEdApplication = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:bEdApplicationUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.bEdApplication.id != null) {
                BEdApplication.update($scope.bEdApplication, onSaveSuccess, onSaveError);
            } else {
                BEdApplication.save($scope.bEdApplication, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
