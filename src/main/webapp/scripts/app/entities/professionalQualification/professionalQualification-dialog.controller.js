'use strict';

angular.module('stepApp').controller('ProfessionalQualificationDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'ProfessionalQualification', 'Employee', 'Institute',
        function($scope, $stateParams, $modalInstance, entity, ProfessionalQualification, Employee, Institute) {

        $scope.professionalQualification = entity;
        $scope.employees = Employee.query();
        $scope.institutes = Institute.query();
        $scope.load = function(id) {
            ProfessionalQualification.get({id : id}, function(result) {
                $scope.professionalQualification = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:professionalQualificationUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.professionalQualification.id != null) {
                ProfessionalQualification.update($scope.professionalQualification, onSaveSuccess, onSaveError);
            } else {
                ProfessionalQualification.save($scope.professionalQualification, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
