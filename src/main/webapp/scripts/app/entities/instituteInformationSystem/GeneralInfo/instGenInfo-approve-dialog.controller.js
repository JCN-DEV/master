'use strict';

angular.module('stepApp')
	.controller('InstGenInfoApproveController',
    ['$scope', '$modalInstance', 'Institute', '$state', 'entity', 'InstGenInfo','InstGenInfoAllApprove',
    function($scope, $modalInstance, Institute, $state, entity, InstGenInfo, InstGenInfoAllApprove) {
       $scope.instGenInfo = entity;
       $scope.institute = {};
        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:instituteUpdate', result);
            console.log("success");
            $scope.instGenInfo.status = 1;
            $scope.instGenInfo.id = $scope.holdId;
            $scope.instGenInfo.institute = result;
            InstGenInfo.update($scope.instGenInfo, onInstGenInfoSaveSuccess, onInstGenInfoSaveError);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.clear = function() {
            $modalInstance.close();
        };
        $scope.confirmApprove = function () {
            InstGenInfoAllApprove.update($scope.instGenInfo, onInstGenInfoSaveSuccess, onInstGenInfoSaveError);
            $scope.isSaving = true;
            /*if ($scope.instGenInfo.institute != null) {
                console.log("success1");
                $scope.holdId = $scope.instGenInfo.id;

                $scope.institute = $scope.instGenInfo.institute;
                $scope.institute.type = $scope.instGenInfo.type;
                $scope.institute.code = $scope.instGenInfo.code;
                $scope.institute.instCategory = $scope.instGenInfo.instCategory;
                $scope.institute.eiin = $scope.instGenInfo.eiin;
                $scope.institute.centerCode = $scope.instGenInfo.centerCode;
                $scope.institute.name = $scope.instGenInfo.name;
                $scope.institute.publicationDate = $scope.instGenInfo.publicationDate;
                $scope.institute.firstAffiliationDate = $scope.instGenInfo.firstAffiliationDate;
                $scope.institute.instLevel = $scope.instGenInfo.instLevel;
                $scope.institute.mpoEnlisted = $scope.instGenInfo.mpoEnlisted;
                $scope.institute.dateOfMpo = $scope.instGenInfo.dateOfMpo;
                $scope.institute.locality = $scope.instGenInfo.locality;
                $scope.institute.ownerType = $scope.instGenInfo.ownerType;
                $scope.institute.mobile = $scope.instGenInfo.mobileNo;
                $scope.institute.altMobile = $scope.instGenInfo.altMobileNo;
                $scope.institute.type = $scope.instGenInfo.type;
                $scope.institute.category = $scope.instGenInfo.category;
                $scope.institute.village = $scope.instGenInfo.village;
                $scope.institute.postOffice = $scope.instGenInfo.postOffice;
                $scope.institute.postCode = $scope.instGenInfo.postCode;
                $scope.institute.landPhone = $scope.instGenInfo.landPhone;
                $scope.institute.mobileNo = $scope.instGenInfo.mobileNo;
                $scope.institute.email = $scope.instGenInfo.email;
                $scope.institute.website = $scope.instGenInfo.website;
                $scope.institute.faxNum = $scope.instGenInfo.faxNum;
                $scope.institute.consArea = $scope.instGenInfo.consArea;
                $scope.institute.division = $scope.instGenInfo.division;
                $scope.institute.instCategory = $scope.instGenInfo.instCategory;
                $scope.institute.upazila = $scope.instGenInfo.upazila;


                //$scope.instGenInfo.userId = $scope.instGenInfo.institute.id;
                console.log('>>>>>>>>>>>>>>>>>>>>>>>>>>');
                console.log($scope.institute);
                Institute.update($scope.institute, onSaveSuccess, onSaveError);
            } else {
                console.log("success2");
                if($scope.instGenInfo.status==null){
                    $scope.instGenInfo.status=0;
                }
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
               *//* $scope.instGenInfo.category = 'School';*//*
                $scope.instGenInfo.lastMpoIncludeExpireDate = $scope.instGenInfo.publicationDate;
                Institute.save($scope.instGenInfo, onSaveSuccess, onSaveError);
            }
*/
        };
        var onInstGenInfoSaveSuccess = function (result) {
            $modalInstance.close(result);
            console.log(result);
            $scope.isInstGenInfoSaving = true;
            $state.go('instituteInfo.pendingInstituteList',{},{reload:true});


        };

        var onInstGenInfoSaveError = function (result) {
            $scope.isInstGenInfoSaving = false;

        };

    }])
    .controller('InstGenInfoDeclineController',
    ['$scope', '$modalInstance', 'Institute', '$state', 'entity', 'InstGenInfo',
    function($scope, $modalInstance, Institute, $state, entity, InstGenInfo) {
            $scope.decline = function(){


            };

            $scope.clear = function() {
                $modalInstance.close();
            };
    }]);
