'use strict';

angular.module('stepApp')
	.controller('InstInfraInfoApproveController',
    ['$scope', '$q', '$modalInstance', 'InstituteInfraInfoByInstitute', 'InstituteLandByInstitute', 'InstituteBuildingByInstitute', 'Institute', '$state', 'entity', 'InstInfraInfo','InstituteBuilding','InstituteInfraInfo','InstituteLand',
    function($scope, $q, $modalInstance, InstituteInfraInfoByInstitute, InstituteLandByInstitute, InstituteBuildingByInstitute, Institute, $state, entity, InstInfraInfo,InstituteBuilding,InstituteInfraInfo,InstituteLand) {
        $scope.infraInfo = entity;
        $q.all([$scope.infraInfo.$promise]).then(function() {
            $scope.inStituteInfra = $scope.infraInfo;
            console.log($scope.inStituteInfra);
            InstituteInfraInfoByInstitute.get({id: $scope.infraInfo.institute.id},function(result){
                console.log(result);
                $scope.tempInstituteInfraInfo = result;
                $scope.tempInstituteLand = result.instLand;
                $scope.tempInstituteBuilding = result.instBuilding;
            },function(result){
                console.log(result);
                $scope.tempInstituteInfraInfo = {};
                $scope.tempInstituteLand = {};
                $scope.tempInstituteBuilding = {};
                console.log($scope.tempInstituteInfraInfo);
                console.log($scope.tempInstituteLand);
                console.log($scope.tempInstituteBuilding);

            });
            return $scope.infraInfo.$promise;
        });
       /* var onInstInfraStatusUpdateError = function (result) {
            $scope.isSaving = false;
        };
        var onInstBuildingSaveError = function (result) {
            $scope.isSaving = false;
        };
        var onSaveError = function (result) {
            console.log("error");
            $scope.isSaving = false;
        };*/
        var onSaveSuccess = function (result) {
           // $scope.$emit('stepApp:instituteUpdate', result);
            console.log("success");
            /*$scope.insAcademicInfo.status = 1;
            $scope.instGenInfo.id = $scope.holdId;
            InsAcademicInfo.update($scope.instGenInfo, onInstGenInfoSaveSuccess, onInstGenInfoSaveError);*/
            $modalInstance.close(result);
            $scope.isSaving = false;
            //$state.go('instGenInfo.InstInfraInfo');
        };

        /*var onInstBuildingSave = function (result) {
           // $scope.$emit('stepApp:instInfraInfoUpdate', result);
            $scope.isSaving = false;
            $scope.instInfraInfo.instBuilding = result;
            Institute.save($scope.instLand, onInstLandSave, onInstBuildingSaveError);
            //InstLand.save($scope.instLand, onInstLandSaveSuccess, onInstLandSaveError);
        };
        var onInstLandSave = function (result) {
            $scope.$emit('stepApp:instInfraInfoUpdate', result);
            $scope.isSaving = false;
            $scope.instInfraInfo.instLand = result;
            //InstInfraInfo.save($scope.instInfraInfo, onInstInfraInfoSaveSuccess, onInstInfraInfoSaveError);
            InstLand.save($scope.instLand, onSaveSuccess, onSaveError);
        };*/
        var onInstBuildingSaveSuccess = function (result) {
/*            $scope.$emit('stepApp:instInfraInfoUpdate', result);*/
            /*$scope.isSaving = false;*/
            $scope.inStituteInfra.instBuilding = result
            if(!$scope.tempInstituteLand.id){
                $scope.inStituteInfra.instLand.id=null;
                InstituteLand.save( $scope.inStituteInfra.instLand, onInstLandSaveSuccess, onInstLandSaveError);
            }
            else{
                $scope.inStituteInfra.instLand.id = $scope.tempInstituteLand.id;
                InstituteLand.update( $scope.inStituteInfra.instLand, onInstLandSaveSuccess, onInstLandSaveError);
            }

        };
        var onInstLandSaveSuccess = function (result) {
            $scope.$emit('stepApp:instInfraInfoUpdate', result);
            $scope.isSaving = false;
            $scope.inStituteInfra.instLand = result;
            if(!$scope.tempInstituteInfraInfo.id){
                $scope.inStituteInfra.id = null;
                console.log($scope.inStituteInfra);
                console.log($scope.inStituteInfra.instLand);
                console.log($scope.inStituteInfra.instBuilding);
                InstituteInfraInfo.save($scope.inStituteInfra, onInstInfraInfoSaveSuccess, onInstInfraInfoSaveError);
            }
            else{
                console.log($scope.inStituteInfra);
                InstituteInfraInfo.update($scope.inStituteInfra, onInstInfraInfoSaveSuccess, onInstInfraInfoSaveError);
            }

        };
        var onInstInfraInfoSaveSuccess = function (result) {
            $scope.$emit('stepApp:instInfraInfoUpdate', result);
            $scope.isSaving = false;
            // $state.go('instGenInfo.instInfraInfo');
            $modalInstance.close(result);
            $state.go('instituteInfo.approve',{},{reload:true});
        };

        var onInstBuildingSaveError = function (result) {
            $scope.isSaving = false;
        };
        var onInstLandSaveError = function (result) {
            $scope.isSaving = false;
        };

        var onInstInfraInfoSaveError = function (result) {
            $scope.isSaving = false;
        };
        var onInstInfraStatusUpdateError = function (result) {
            $scope.isSaving = false;
        };

        var onInstBuildingSave = function (result) {
            $scope.isSaving = true;
            if(!$scope.tempInstituteBuilding.id){
                $scope.inStituteInfra.instBuilding.id = null;
                InstituteBuilding.save($scope.inStituteInfra.instBuilding, onInstBuildingSaveSuccess, onInstBuildingSaveError);
            }
            else{
                $scope.inStituteInfra.instBuilding.id=$scope.tempInstituteBuilding.id;
                InstituteBuilding.update($scope.inStituteInfra.instBuilding, onInstBuildingSaveSuccess, onInstBuildingSaveError);
            }

           /* if ($scope.instInfraInfo.id != null) {
                InstBuilding.update($scope.instBuilding, onInstBuildingSaveSuccess, onInstBuildingSaveError);
            } else {
                $scope.instInfraInfo.status = 0;
                InstBuilding.save($scope.instBuilding, onInstBuildingSaveSuccess, onInstBuildingSaveError);
            }*/
        };
        /*var onInstInfraInfoSaveSuccess = function (result) {
            $scope.$emit('stepApp:instInfraInfoUpdate', result);
            $scope.isSaving = false;
            // $state.go('instGenInfo.instInfraInfo');
            $state.go('instituteInfo.infrastructureInfo',{},{reload:true});
        };*/




        $scope.clear = function() {
            console.log("cancel inst InstInfraInfo ");
            $modalInstance.dismiss('cancel');
            $state.go('instituteInfo.approve');
            //$state.go('instGenInfo.InstInfraInfo');
        };

        $scope.confirmApprove = function () {
            $scope.isSaving = true;
            $scope.infraInfo.status = 1;
            //InstInfraInfo.update($scope.infraInfo, onSaveSuccess, onSaveError);
            //InstInfraInfo.update($scope.infraInfo, onInstInfraStatusUpdateSuccess, onInstInfraStatusUpdateSuccess);
            //InstBuilding.update($scope.instBuilding, onInstBuildingSaveSuccess, onInstBuildingSaveError);
            InstInfraInfo.update($scope.infraInfo, onInstBuildingSave, onInstInfraStatusUpdateError);
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
            $state.go('instGenInfo.InstInfraInfo');

        };

        var onInstGenInfoSaveError = function (result) {
            $scope.isInstGenInfoSaving = false;

        };

    }])
    .controller('InstInfraInfoDeclineController',
    ['$scope', '$modalInstance', '$stateParams', 'Institute', '$state', 'InstInfraInfo','InstInfraInfoDecline',
    function($scope, $modalInstance, $stateParams, Institute, $state, InstInfraInfo,InstInfraInfoDecline) {
        $scope.clear = function() {
            console.log("cancel inst academic info");
            $modalInstance.close();
        };
        console.log($stateParams.id);
        $scope.decline = function(){
            InstInfraInfoDecline.update({id: $stateParams.id}, $scope.causeDeny);
            $modalInstance.close();
        }

    }]);
