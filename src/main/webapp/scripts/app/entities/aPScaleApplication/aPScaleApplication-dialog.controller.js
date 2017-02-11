'use strict';

angular.module('stepApp').controller('APScaleApplicationDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'APScaleApplication', 'InstEmployee',
        function($scope, $stateParams, $modalInstance, entity, APScaleApplication, InstEmployee) {

        $scope.aPScaleApplication = entity;
        $scope.instemployees = InstEmployee.query();
        $scope.load = function(id) {
            APScaleApplication.get({id : id}, function(result) {
                $scope.aPScaleApplication = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:aPScaleApplicationUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.aPScaleApplication.id != null) {
                APScaleApplication.update($scope.aPScaleApplication, onSaveSuccess, onSaveError);
            } else {
                APScaleApplication.save($scope.aPScaleApplication, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
