'use strict';

angular.module('stepApp').controller('ReferenceDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Reference', 'Employee', 'User',
        function($scope, $stateParams, $modalInstance, entity, Reference, Employee, User) {

        $scope.reference = entity;
        $scope.employees = Employee.query();
        $scope.users = User.query();
        $scope.load = function(id) {
            Reference.get({id : id}, function(result) {
                $scope.reference = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:referenceUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.reference.id != null) {
                Reference.update($scope.reference, onSaveSuccess, onSaveError);
            } else {
                Reference.save($scope.reference, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
