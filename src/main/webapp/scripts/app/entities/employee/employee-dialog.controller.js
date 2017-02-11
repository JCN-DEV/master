'use strict';

angular.module('stepApp').controller('EmployeeDialogController',
    ['$scope', '$stateParams', '$modalInstance', '$q', 'entity', 'Employee', 'User', 'Institute', 'PayScale', 'ApplicantEducation', 'Training', 'Skill', 'Reference', 'Lang','DataUtils',
        function($scope, $stateParams, $modalInstance, $q, entity, Employee, User, Institute, PayScale, ApplicantEducation, Training, Skill, Reference, Lang, DataUtils) {

        $scope.employee = entity;
        $scope.users = User.query();
        $scope.institutes = Institute.query();
        $scope.payscales = PayScale.query();
        $scope.applicanteducations = ApplicantEducation.query();
        $scope.trainings = Training.query();
        $scope.skills = Skill.query();
        $scope.references = Reference.query();
        $scope.langs = Lang.query();
        $scope.load = function(id) {
            Employee.get({id : id}, function(result) {
                $scope.employee = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:employeeUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.employee.id != null) {
                Employee.update($scope.employee, onSaveSuccess, onSaveError);
            } else {
                Employee.save($scope.employee, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };

            $scope.byteSize = DataUtils.byteSize;

            $scope.setCv = function ($file, employee) {
                if ($file) {
                    var fileReader = new FileReader();
                    fileReader.readAsDataURL($file);
                    fileReader.onload = function (e) {
                        var base64Data = e.target.result.substr(e.target.result.indexOf('base64,') + 'base64,'.length);
                        $scope.$apply(function() {
                            employee.cv = base64Data;
                            employee.cvContentType = $file.type;
                        });
                    };
                }
            };
}]);
