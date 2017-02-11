'use strict';

angular.module('stepApp').controller('GradeSetupDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'GradeSetup',
        function($scope, $stateParams, $modalInstance, entity, GradeSetup) {

        $scope.gradeSetup = entity;
        $scope.load = function(id) {
            GradeSetup.get({id : id}, function(result) {
                $scope.gradeSetup = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:gradeSetupUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.gradeSetup.id != null) {
                GradeSetup.update($scope.gradeSetup, onSaveSuccess, onSaveError);
            } else {
                GradeSetup.save($scope.gradeSetup, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
