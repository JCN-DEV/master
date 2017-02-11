'use strict';

angular.module('stepApp')
	.controller('InstEmpCountApproveController',
    ['$scope', '$q', '$modalInstance', 'Institute', '$state', 'entity', 'InstEmpCount','InstituteEmpCount','InstituteEmpCountByInstitute',
    function($scope, $q, $modalInstance, Institute, $state, entity, InstEmpCount,InstituteEmpCount,InstituteEmpCountByInstitute) {
        $scope.instEmpCount = entity;
        $scope.tempInstituteEmpCount = entity;

       $q.all([$scope.instEmpCount.$promise]).then(function(){
         if($scope.instEmpCount.institute != null){
             InstituteEmpCountByInstitute.get({id:$scope.instEmpCount.institute.id}, function(response){
                 $scope.instituteEmpCount = response;
             });
         }
         else{
             $scope.instituteEmpCount = null;
         }

           return $scope.instEmpCount.$promise;
       });


        var onSaveSuccess = function (result) {
            /*$scope.$emit('stepApp:instituteUpdate', result);*/
            console.log("success");
            /*$scope.insAcademicInfo.status = 1;
            $scope.instGenInfo.id = $scope.holdId;
            InsAcademicInfo.update($scope.instGenInfo, onInstGenInfoSaveSuccess, onInstGenInfoSaveError);*/
            $modalInstance.close(result);
            $scope.isSaving = false;
            //$state.go('instGenInfo.InstEmpCount');
            $state.go('instituteInfo.approve',{},{reload:true});
        };
        var onSaveInstituteEmpCountSave = function (result) {
            console.log($scope.instEmpCount.institute.id);
            /*$scope.tmpinstituteEmpCount=InstituteEmpCountByInstitute.get({id:$scope.instEmpCount.institute.id});
           console.log($scope.tmpinstituteEmpCount);
            if($scope.tmpinstituteEmpCount.id !=null){
                console.log('edit ');
                $scope.instituteEmpCount.id= $scope.tmpinstituteEmpCount.id;
                //InstituteEmpCount.update($scope.instituteEmpCount, onSaveSuccess, onSaveError);
            }else{
                console.log('new ');
                $scope.instituteEmpCount.id=null;
                //InstituteEmpCount.save($scope.instituteEmpCount, onSaveSuccess, onSaveError);
            }*/

            if($scope.instituteEmpCount !=null){
                $scope.tempInstituteEmpCount.id = $scope.instituteEmpCount.id;
                InstituteEmpCount.update($scope.tempInstituteEmpCount, onSaveSuccess, onSaveError);
            }
            else{
                $scope.instEmpCount.id = null;
                InstituteEmpCount.save($scope.tempInstituteEmpCount, onSaveSuccess, onSaveError);
            }


            /*InstituteEmpCountByInstitute.get({id:$scope.instEmpCount.institute.id}, function(response){
                $scope.tmpinstituteEmpCount = response;
                console.log('edit >>>>> ');
                $scope.instituteEmpCount.id= response.id;
                console.log($scope.instituteEmpCount.id);
                InstituteEmpCount.update($scope.instituteEmpCount, onSaveSuccess, onSaveError);

            }, function(response){
                if(response.status == 404) {
                    console.log('new ');
                    $scope.instituteEmpCount.id=null;
                    InstituteEmpCount.save($scope.instituteEmpCount, onSaveSuccess, onSaveError);
                }
            });*/

        };

        var onSaveError = function (result) {
            console.log("error");
            $scope.isSaving = false;
        };

        $scope.clear = function() {
            console.log("cancel inst academic info");
            $modalInstance.dismiss('cancel');
            $state.go('instituteInfo.approve',{},{reload:true});
        };
        $scope.confirmApprove = function () {
            $scope.isSaving = true;
            $scope.instEmpCount.status = 1;
            //onSaveInstituteEmpCountSave();
            InstEmpCount.update($scope.instEmpCount, onSaveInstituteEmpCountSave, onSaveError);
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
            $state.go('instGenInfo.InstEmpCount');

        };

        var onInstGenInfoSaveError = function (result) {
            $scope.isInstGenInfoSaving = false;

        };

    }])
    .controller('InstEmpCountDeclineController',
    ['$scope','$stateParams', '$modalInstance', 'Institute', '$state', 'entity', 'InstEmpCount','InstEmpCountDecline',
    function($scope,$stateParams, $modalInstance, Institute, $state, entity, InstEmpCount,InstEmpCountDecline) {
        $scope.clear = function() {
            console.log("cancel inst academic info");
            $modalInstance.close();
        };
        console.log($stateParams.id);
        $scope.decline = function(){
            InstEmpCountDecline.update({id: $stateParams.id}, $scope.causeDeny);
            $modalInstance.close();
        }
    }]);
