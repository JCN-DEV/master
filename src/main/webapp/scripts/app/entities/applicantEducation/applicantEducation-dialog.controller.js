'use strict';

angular.module('stepApp').controller('ApplicantEducationDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'ApplicantEducation', 'Employee', 'Institute', 'User',
        function($scope, $stateParams, $modalInstance, entity, ApplicantEducation, Employee, Institute, User) {

        $scope.applicantEducation = entity;
        $scope.employees = Employee.query();
        $scope.institutes = Institute.query();
        $scope.users = User.query();
        $scope.load = function(id) {
            ApplicantEducation.get({id : id}, function(result) {
                $scope.applicantEducation = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:applicantEducationUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.applicantEducation.id != null) {
                ApplicantEducation.update($scope.applicantEducation, onSaveSuccess, onSaveError);
            } else {
                ApplicantEducation.save($scope.applicantEducation, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
