'use strict';

angular.module('stepApp').controller('ExperienceFormController',
    ['$scope', '$stateParams', '$modalInstance', 'DataUtils', 'entity', 'Experience', 'Employee', 'Institute', 'User',
        function($scope, $stateParams, $modalInstance, DataUtils, entity, Experience, Employee, Institute, User) {

        $scope.experience = entity;
        $scope.employees = Employee.query({size: 500});
        $scope.institutes = Institute.query({size: 500});
        $scope.users = User.query({size: 500});
        $scope.load = function(id) {
            Experience.get({id : id}, function(result) {
                $scope.experience = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:experienceUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.experience.id != null) {
                Experience.update($scope.experience, onSaveSuccess, onSaveError);
            } else {
                Experience.save($scope.experience, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };

        $scope.abbreviate = DataUtils.abbreviate;

        $scope.byteSize = DataUtils.byteSize;

        $scope.setAttachment = function ($file, experience) {
            if ($file) {
                var fileReader = new FileReader();
                fileReader.readAsDataURL($file);
                fileReader.onload = function (e) {
                    var base64Data = e.target.result.substr(e.target.result.indexOf('base64,') + 'base64,'.length);
                    $scope.$apply(function() {
                        experience.attachment = base64Data;
                        experience.attachmentContentType = $file.type;
                    });
                };
            }
        };
}]);
