'use strict';

angular.module('stepApp')
	.controller('InsAcademicInfoApproveController',
    ['$scope','$location','$rootScope', '$modalInstance', 'Institute', '$state',  'InsAcademicInfo','$stateParams',
    function($scope,$location,$rootScope, $modalInstance, Institute, $state,  InsAcademicInfo,$stateParams) {
       //$scope.insAcademicInfo = entity;
       $scope.insAcademicInfo = InsAcademicInfo.get({id : $stateParams.acaid});

        console.log($stateParams);
        var onSaveSuccess = function (result) {
           // $scope.$emit('stepApp:instituteUpdate', result);
            console.log("success");
            /*$scope.insAcademicInfo.status = 1;
            $scope.instGenInfo.id = $scope.holdId;
            InsAcademicInfo.update($scope.instGenInfo, onInstGenInfoSaveSuccess, onInstGenInfoSaveError);*/

            $scope.isSaving = false;
            $modalInstance.close(result);

            /*$location.path("#/institute-info/approve/"+$stateParams.id);*/
            /*$state.go('^',{},{reload:true});*/
        };

        var onSaveError = function (result) {
            console.log("error");
            $scope.isSaving = false;
        };

        $scope.clear = function() {
            console.log("cancel inst academic info");
            $modalInstance.close();
        };
        $scope.confirmApprove = function () {
            $scope.isSaving = true;
            $scope.insAcademicInfo.status = 1;

            console.log($scope.insAcademicInfo);
            onSaveSuccess( $scope.insAcademicInfo);
            InsAcademicInfo.update($scope.insAcademicInfo, onSaveSuccess, onSaveError);
            /*if ($scope.insAcademicInfo.institute != null) {
                console.log("success1");
                Institute.update($scope.instGenInfo, onSaveSuccess, onSaveError);
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
//          $state.go('instGenInfo.generalinfo');
            $state.go('instituteInfo.pendingInstituteList');

        };

        var onInstGenInfoSaveError = function (result) {
            $scope.isInstGenInfoSaving = false;

        };

    }])
    .controller('InsAcademicInfoDeclineController',
    ['$scope', '$state', '$modalInstance', '$location','$rootScope', '$stateParams','InstituteAcademicInfoDecline',
    function($scope, $state, $modalInstance, $location,$rootScope, $stateParams,InstituteAcademicInfoDecline) {
        $scope.clear = function() {
            console.log("cancel inst academic info");
            $modalInstance.close();
        };
        console.log($stateParams.id);
        $scope.decline = function(){
            InstituteAcademicInfoDecline.update({id: $stateParams.id}, $scope.causeDeny);
            $modalInstance.close();
        }
    }]);
