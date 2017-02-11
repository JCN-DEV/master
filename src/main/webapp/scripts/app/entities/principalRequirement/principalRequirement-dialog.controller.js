'use strict';

angular.module('stepApp').controller('PrincipalRequirementDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'PrincipalRequirement', 'Employee',
        function($scope, $stateParams, $modalInstance, entity, PrincipalRequirement, Employee) {

        $scope.principalRequirement = entity;
        $scope.employees = Employee.query({size: 500});
        $scope.load = function(id) {
            PrincipalRequirement.get({id : id}, function(result) {
                $scope.principalRequirement = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:principalRequirementUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.principalRequirement.id != null) {
                PrincipalRequirement.update($scope.principalRequirement, onSaveSuccess, onSaveError);
            } else {
                PrincipalRequirement.save($scope.principalRequirement, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
