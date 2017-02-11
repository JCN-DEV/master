'use strict';

angular.module('stepApp')
	.controller('InstAdmInfoApproveController',
	['$scope','$modalInstance','Institute','$state','entity','InstAdmInfo',
	 function($scope, $modalInstance, Institute, $state, entity, InstAdmInfo) {
       $scope.instAdmInfo = entity;
        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:instituteUpdate', result);
            console.log("success");
            /*$scope.insAcademicInfo.status = 1;
            $scope.instGenInfo.id = $scope.holdId;
            InsAcademicInfo.update($scope.instGenInfo, onInstGenInfoSaveSuccess, onInstGenInfoSaveError);*/
            $modalInstance.close(result);
            $scope.isSaving = false;
            $state.go('instGenInfo.instAdmInfo');
        };

        var onSaveError = function (result) {
            console.log("error");
            $scope.isSaving = false;
        };

        $scope.clear = function() {
            console.log("cancel inst academic info");
            $modalInstance.dismiss('cancel');
            $state.go('instGenInfo.instAdmInfo');
        };
        $scope.confirmApprove = function () {
            $scope.isSaving = true;
            $scope.instAdmInfo.status = 1;
            InstAdmInfo.update($scope.instAdmInfo, onSaveSuccess, onSaveError);
            /*if ($scope.instGenInfo.institute != null) {
                console.log("success1");
                Institu.update($scope.instGenInfo, onSaveSuccess, onSaveError);
            } else {
                console.log("success2");
                $scope.holdId = $scope.instGenInfo.id;
                $scope.instGenInfo.id = null;
                $scope.instGenInfo.dateOfEstablishment = $scope.instGenInfo.publicationDate;
                $scope.instGenInfo.firstApprovedEducationalYear = $scope.instGenInfo.publicationDate;
                $scope.instGenInfo.lastApprovedEducationalYear = $scope.instGenInfo.publicationDate;
                $scope.instGenInfo.firstMpoIncludeDate = $scope.instGenInfo.publicationDate;
                $scope.instGenInfo.lastMpoExpireDate = $scope.instGenInfo.publicationDate;
                $scope.instGenInfo.nameOfTradeSubject = $scope.instGenInfo.publicationDate;
                $scope.instGenInfo.lastApprovedSignatureDate = $scope.instGenInfo.publicationDate;
                $scope.instGenInfo.lastCommitteeApprovedDate = $scope.instGenInfo.publicationDate;
                $scope.instGenInfo.lastCommitteeApprovedFileContentType = $scope.instGenInfo.publicationDate;
                $scope.instGenInfo.lastCommitteeExpDate = $scope.instGenInfo.publicationDate;
                $scope.instGenInfo.lastCommittee1stMeetingFileContentType = " ";
                $scope.instGenInfo.lastCommitteeExpireDate = $scope.instGenInfo.publicationDate;
                $scope.instGenInfo.lastMpoMemorialDate = $scope.instGenInfo.publicationDate;
                $scope.instGenInfo.lengthOfLibrary = 0;
                $scope.instGenInfo.widthOfLibrary = 0;
                $scope.instGenInfo.lastMpoIncludeExpireDate = $scope.instGenInfo.publicationDate;

                Institute.save($scope.instGenInfo, onSaveSuccess, onSaveError);
            }*/

        };
        var onInstGenInfoSaveSuccess = function (result) {
            console.log(result);
            $scope.isInstGenInfoSaving = true;
            $state.go('instGenInfo.generalinfo');

        };

        var onInstGenInfoSaveError = function (result) {
            $scope.isInstGenInfoSaving = false;

        };

    }]);
