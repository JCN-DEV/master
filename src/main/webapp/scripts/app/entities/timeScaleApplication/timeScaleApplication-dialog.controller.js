'use strict';

angular.module('stepApp').controller('TimeScaleApplicationDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'TimeScaleApplication', 'InstEmployee',
        function($scope, $stateParams, $modalInstance, entity, TimeScaleApplication, InstEmployee) {

        $scope.timeScaleApplication = entity;
        $scope.instemployees = InstEmployee.query();
        $scope.load = function(id) {
            TimeScaleApplication.get({id : id}, function(result) {
                $scope.timeScaleApplication = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:timeScaleApplicationUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.timeScaleApplication.id != null) {
                TimeScaleApplication.update($scope.timeScaleApplication, onSaveSuccess, onSaveError);
            } else {
                TimeScaleApplication.save($scope.timeScaleApplication, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
