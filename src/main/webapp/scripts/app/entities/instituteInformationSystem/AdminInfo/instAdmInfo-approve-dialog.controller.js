'use strict';

angular.module('stepApp')
	.controller('InstAdmInfoApproveController',
    ['$scope','$location', '$rootScope', '$modalInstance', 'Institute', '$state', 'entity', 'InstAdmInfo','$stateParams',
    function($scope,$location, $rootScope, $modalInstance, Institute, $state, entity, InstAdmInfo,$stateParams) {
       //$scope.instAdmInfo = entity;
       $scope.instAdmInfo = InstAdmInfo.get({id : $stateParams.admid});
        console.log($stateParams);
        var onSaveSuccess = function (result) {
            console.log("success");
            console.log(result);

            //$scope.$emit('stepApp:', result);
            console.log("success");
            /*$scope.insAcademicInfo.status = 1;
            $scope.instGenInfo.id = $scope.holdId;
            InsAcademicInfo.update($scope.instGenInfo, onInstGenInfoSaveSuccess, onInstGenInfoSaveError);*/

            $modalInstance.close(result);
            $scope.isSaving = false;
            $location.path("#/institute-info/approve/"+$stateParams.id);

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
            $scope.instAdmInfo.status = 1;
            InstAdmInfo.update($scope.instAdmInfo, onSaveSuccess, onSaveError);
            //onSaveSuccess( $scope.instAdmInfo);
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

        };

        var onInstGenInfoSaveError = function (result) {
            $scope.isInstGenInfoSaving = false;

        };

    }])
    .controller('InstAdmInfoDeclineController',
    ['$scope','$location', '$rootScope', '$modalInstance', 'Institute', '$state', 'entity', 'InstAdmInfo','$stateParams','InstAdmInfoDecline',
    function($scope,$location, $rootScope, $modalInstance, Institute, $state, entity, InstAdmInfo,$stateParams,InstAdmInfoDecline) {
        $scope.clear = function() {
            console.log("cancel inst academic info");
            $modalInstance.close();
        };
        console.log($stateParams.id);
        $scope.decline = function(){
            console.log();
            InstAdmInfoDecline.update({id: $stateParams.id}, $scope.causeDeny);
            $modalInstance.close();
        }
    }]);
