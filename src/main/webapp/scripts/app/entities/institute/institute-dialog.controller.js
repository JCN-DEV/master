'use strict';

angular.module('stepApp').controller('InstituteDialogController',
    ['$scope', '$stateParams', '$modalInstance', '$q', 'DataUtils', 'entity', 'Institute', 'User', 'Upazila', 'Course',
        function($scope, $stateParams, $modalInstance, $q, DataUtils, entity, Institute, User, Upazila, Course) {

        $scope.institute = entity;
        $scope.users = User.query({size:500});
        $scope.upazilas = Upazila.query({size:500});
        $scope.courses = Course.query({size:500});

        $scope.load = function(id) {
            Institute.get({id : id}, function(result) {
                $scope.institute = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:instituteUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.institute.id != null) {
                Institute.update($scope.institute, onSaveSuccess, onSaveError);
            } else {
                Institute.save($scope.institute, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };

        $scope.abbreviate = DataUtils.abbreviate;

        $scope.byteSize = DataUtils.byteSize;

        $scope.setLastCommitteeApprovedFile = function ($file, institute) {
            if ($file) {
                var fileReader = new FileReader();
                fileReader.readAsDataURL($file);
                fileReader.onload = function (e) {
                    var base64Data = e.target.result.substr(e.target.result.indexOf('base64,') + 'base64,'.length);
                    $scope.$apply(function() {
                        institute.lastCommitteeApprovedFile = base64Data;
                        institute.lastCommitteeApprovedFileContentType = $file.type;
                    });
                };
            }
        };

        $scope.setLastCommittee1stMeetingFile = function ($file, institute) {
            if ($file) {
                var fileReader = new FileReader();
                fileReader.readAsDataURL($file);
                fileReader.onload = function (e) {
                    var base64Data = e.target.result.substr(e.target.result.indexOf('base64,') + 'base64,'.length);
                    $scope.$apply(function() {
                        institute.lastCommittee1stMeetingFile = base64Data;
                        institute.lastCommittee1stMeetingFileContentType = $file.type;
                    });
                };
            }
        };
}]);
