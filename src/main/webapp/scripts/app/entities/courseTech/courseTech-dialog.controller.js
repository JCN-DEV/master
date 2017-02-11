'use strict';

angular.module('stepApp').controller('CourseTechDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'CourseTech',
        function($scope, $stateParams, $modalInstance, entity, CourseTech) {

        $scope.courseTech = entity;
        $scope.load = function(id) {
            CourseTech.get({id : id}, function(result) {
                $scope.courseTech = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:courseTechUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.courseTech.id != null) {
                CourseTech.update($scope.courseTech, onSaveSuccess, onSaveError);
            } else {
                CourseTech.save($scope.courseTech, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
