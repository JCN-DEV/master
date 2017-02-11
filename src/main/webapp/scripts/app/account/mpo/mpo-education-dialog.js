'use strict';

angular.module('stepApp').controller('MPOEmployeeEducationDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'ApplicantEducation', 'Employee', 'Institute',
    function ($scope, $stateParams, $modalInstance, entity, ApplicantEducation, Employee, Institute) {

        $scope.educationName = ['SSC/Dakhil', 'SSC (Vocational)', 'HSC/Alim', 'HSC (Vocational)', 'HSC (BM)', 'Honors/Degree', 'Masters']
        $scope.employee = entity;
        $scope.calendar = {
            opened: {},
            dateFormat: 'yyyy-MM-dd',
            dateOptions: {},
            open: function ($event, which) {
                $event.preventDefault();
                $event.stopPropagation();
                $scope.calendar.opened[which] = true;
            }
        };


        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:MpoApplicantEducationEdit', result);
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
                $scope.applicantEducation.employee = {};
                $scope.applicantEducation.manager = $scope.employee.manager;
                $scope.applicantEducation.institute = $scope.employee.institute;
                $scope.applicantEducation.employee.id = $scope.employee.id;
                ApplicantEducation.save($scope.applicantEducation, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function () {
            $modalInstance.dismiss('cancel');
        };
    }]);
