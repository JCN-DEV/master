'use strict';

angular.module('stepApp').controller('JobPostingInfoDialogController',
    ['$scope','$rootScope','$state', 'DataUtils', 'entity', 'JobPostingInfo', 'JobType',
        function($scope,$rootScope,$state,DataUtils, entity, JobPostingInfo, JobType) {

        $scope.jobPostingInfo = entity;
        $scope.jobtypes = JobType.query();
        $scope.load = function(id) {
            JobPostingInfo.get({id : id}, function(result) {
                $scope.jobPostingInfo = result;
            });
        };

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
            $scope.$emit('stepApp:jobPostingInfoUpdate', result);
            /*$modalInstance.close(result);*/
            $scope.isSaving = false;
            /*alert('Hi shekhar');*/
            $state.go('jobPostingInfo');
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.jobPostingInfo.id != null) {
                JobPostingInfo.update($scope.jobPostingInfo, onSaveSuccess, onSaveError);
                $rootScope.setSuccessMessage('stepApp.jobPostingInfo.updated');
            } else {
                JobPostingInfo.save($scope.jobPostingInfo, onSaveSuccess, onSaveError);
                $rootScope.setSuccessMessage('stepApp.jobPostingInfo.created');
            }
        };

            $scope.allowedFileTypes = '';
            $scope.updateContentType = function () {
                /*if ($scope.jobPostingInfo.contentType == 'Notice')*/
                    $scope.allowedFileTypes = '.pdf,.doc,.docx,.rtf,.png,.jpg,.gif,';
                /*else
                    $scope.allowedFileTypes = '.png,.jpg,.gif,';*/

            }

            $scope.setContent = function ($file, jobPostingInfo) {
                if (($file.size / 1024) / 1024 > $scope.appId.limitMb) {
                    jobPostingInfo.uploadContentType = null;
                    jobPostingInfo.contentName = null;
                }
                else {
                    var fileReader = new FileReader();
                    fileReader.readAsDataURL($file);
                    fileReader.onload = function (e) {
                        var base64Data = e.target.result.substr(e.target.result.indexOf('base64,') + 'base64,'.length);
                        $scope.$apply(function () {
                            jobPostingInfo.upload = base64Data;
                            jobPostingInfo.uploadContentType = $file.type;
                            jobPostingInfo.contentName = $file.name;
                        });
                    };
                }
            };

            $scope.setContentImage = function ($file, jobPostingInfo) {

                if ($file) {
                    var fileReader = new FileReader();
                    fileReader.readAsDataURL($file);
                    fileReader.onload = function (e) {
                        var base64Data = e.target.result.substr(e.target.result.indexOf('base64,') + 'base64,'.length);
                        $scope.$apply(function () {
                            jobPostingInfo.upload = base64Data;
                            jobPostingInfo.uploadContentType = $file.type;
                            jobPostingInfo.uploadedFileName = $file.name;
                        });
                    };
                }
            };

        $scope.clear = function() {
           /* $modalInstance.dismiss('cancel');*/
            $state.go('jobPostingInfo');
        };

        $scope.abbreviate = DataUtils.abbreviate;

        $scope.byteSize = DataUtils.byteSize;

        $scope.setUpload = function ($file, jobPostingInfo) {
            if ($file) {
                var fileReader = new FileReader();
                fileReader.readAsDataURL($file);
                fileReader.onload = function (e) {
                    var base64Data = e.target.result.substr(e.target.result.indexOf('base64,') + 'base64,'.length);
                    $scope.$apply(function() {
                        jobPostingInfo.upload = base64Data;
                        jobPostingInfo.uploadContentType = $file.type;
                    });
                };
            }
        };
}]);
