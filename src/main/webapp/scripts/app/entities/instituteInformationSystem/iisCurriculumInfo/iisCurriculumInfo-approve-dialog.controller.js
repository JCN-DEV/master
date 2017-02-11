'use strict';

angular.module('stepApp')
	.controller('IisCurriculumInfoApproveController',
    ['$scope', '$modalInstance', 'Institute', '$state', 'entity', 'InstFinancialInfo','IisCurriculumsByInstituteAndStatus','$stateParams','IisCurriculumInfo',
    function($scope, $modalInstance, Institute, $state, entity, InstFinancialInfo,IisCurriculumsByInstituteAndStatus,$stateParams,IisCurriculumInfo) {
       $scope.iisCurriculums = [];
        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:instituteUpdate', result);
            console.log("success");
            /*$scope.insAcademicInfo.status = 1;
            $scope.instGenInfo.id = $scope.holdId;
            InsAcademicInfo.update($scope.instGenInfo, onInstGenInfoSaveSuccess, onInstGenInfoSaveError);*/
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
            //$state.go('instituteInfo.approve');
        };
        $scope.confirmApprove = function () {
            $scope.isSaving = true;
            IisCurriculumsByInstituteAndStatus.query({instituteId:$stateParams.instid, status:0}, function(result){
                console.log(result);
                $scope.iisCurriculums = result;
                angular.forEach(result, function(data){
                    if(data.id != null){
                        data.status = 3;
                        IisCurriculumInfo.update(data, onSaveSuccess, onSaveError);

                    }

                });
            });


            /*console.log("cancel inst Financial info");
            console.log($stateParams);
            InstFinancialInfoApprove.approve($stateParams.instid,onSaveSuccess, onSaveError);
            //InstFinancialInfo.update($scope.instFinancialInfo, onSaveSuccess, onSaveError);
            if ($scope.instGenInfo.institute != null) {
                console.log("success1");
                Institue.update($scope.instGenInfo, onSaveSuccess, onSaveError);
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

    }}])
    .controller('IisCurriculumInfoDeclineController',
    ['$scope', '$modalInstance', 'Institute', '$state', 'InstFinancialInfo','InstFinancialInfoDecline','$stateParams',
    function($scope, $modalInstance, Institute, $state, InstFinancialInfo,InstFinancialInfoDecline,$stateParams) {
        /*$scope.clear = function() {
            console.log("cancel inst academic info");
            $modalInstance.close();
        };
        $scope.decline = function(){
            InstFinancialInfoDecline.update({id: $stateParams.instid}, $scope.causeDeny);
            $modalInstance.close();
        }*/
    }]);
