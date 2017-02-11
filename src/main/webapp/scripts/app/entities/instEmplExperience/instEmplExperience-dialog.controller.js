'use strict';

angular.module('stepApp').controller('InstEmplExperienceDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'DataUtils', 'entity', 'InstEmplExperience', 'InstEmployee',
        function($scope, $stateParams, $modalInstance, DataUtils, entity, InstEmplExperience, InstEmployee) {

        $scope.instEmplExperience = entity;
        $scope.instemployees = InstEmployee.query();
        $scope.load = function(id) {
            InstEmplExperience.get({id : id}, function(result) {
                $scope.instEmplExperience = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:instEmplExperienceUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.instEmplExperience.id != null) {
                InstEmplExperience.update($scope.instEmplExperience, onSaveSuccess, onSaveError);
            } else {
                InstEmplExperience.save($scope.instEmplExperience, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };

        $scope.abbreviate = DataUtils.abbreviate;

        $scope.byteSize = DataUtils.byteSize;

        $scope.setAttachment = function ($file, instEmplExperience) {
            if ($file) {
                var fileReader = new FileReader();
                fileReader.readAsDataURL($file);
                fileReader.onload = function (e) {
                    var base64Data = e.target.result.substr(e.target.result.indexOf('base64,') + 'base64,'.length);
                    $scope.$apply(function() {
                        instEmplExperience.attachment = base64Data;
                        instEmplExperience.attachmentContentType = $file.type;
                    });
                };
            }
        };
}]);
