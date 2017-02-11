'use strict';

angular.module('stepApp')
	.controller('InstEmployeeInfoApproveController',
	['$scope', '$stateParams','Institute', '$state',  'InstGenInfo','InstEmployeeCode','Principal','$rootScope','IisCourseInfoOfCurrentInstByTrade','InstVacancyCurrentInstForTeacher','InstVacancyCurInstForTeacher',
	 function($scope, $stateParams,Institute, $state,  InstGenInfo,InstEmployeeCode,Principal,$rootScope,IisCourseInfoOfCurrentInstByTrade,InstVacancyCurrentInstForTeacher,InstVacancyCurInstForTeacher) {
       // $scope.account = account;
       $scope.hideApproveAndDecline = false;
        $scope.showEligible = false;
        Principal.identity().then(function (account) {
            $scope.account = account;
            $scope.hideApproveAndDecline = false;
            $scope.showEligible = false;
            InstEmployeeCode.get({'code':$stateParams.code},function(result){

                $scope.instEmployee = result.instEmployee;
                $scope.instEmpAddress = result.instEmpAddress;
                $scope.instEmplBankInfo = result.instEmplBankInfo;
                $scope.instEmplExperiences = result.instEmplExperiences;
                console.log($scope.instEmployee.institute.mpoEnlisted);
                if($scope.instEmplExperiences == null ||  $scope.instEmplExperiences.length < 1){
                    console.log('experience not found for employee');
                    $scope.instEmplExperiences =[{}];
                }
                if($scope.instEmployee.image !=null){
                    var blob = $rootScope.b64toBlob($scope.instEmployee.image, $scope.instEmployee.imageContentType);
                    $scope.url = (window.URL || window.webkitURL).createObjectURL( blob );
                    /* $scope.instEmployee.image=null;*/
                }
                if($scope.instEmployee.status > 0){
                    $scope.hideApproveAndDecline=true;
                }

                //checking 1.institute mpoEnlisted 2.employee status is 2  3.employee mpoApplicatonStatus less than 2
                if($scope.instEmployee.institute.mpoEnlisted && $scope.instEmployee.status === 2 && $scope.instEmployee.mpoAppStatus <2){
                    console.log('-----employee status------'+$scope.instEmployee.status);

                    //checking trade is mpo enlisted
                    IisCourseInfoOfCurrentInstByTrade.get({id:$scope.instEmployee.cmsSubAssign.cmsTrade.id}, function(result){
                        console.log("trade result found");
                        if(result.mpoEnlisted ){
                            console.log("trade is mpo enlisted");

                            //checking vacancy available for this institute and subject
                            //InstVacancyCurInstForTeacher.get({tradeId: $scope.instEmployee.cmsSubAssign.cmsTrade.id, subjectId: $scope.instEmployee.cmsSubAssign.cmsSubject.id}, function(vacancy){
                            InstVacancyCurInstForTeacher.get({subjectId: $scope.instEmployee.cmsSubAssign.cmsSubject.id}, function(vacancy){
                            //InstVacancyCurrentInstForTeacher.get({tradeId: 18068151454, subjectId: 18085151454}, function(vacancy){
                                console.log("result found");
                               if(vacancy.totalVacancy > vacancy.filledUpVacancy){
                                   $scope.showEligible=true;
                               }

                            });

                        }
                    });

                }
                if($scope.instEmployee.mpoAppStatus >=2){
                    console.log('-----employee status------'+$scope.instEmployee.status);
                    $scope.showEligible=false;
                }
                $scope.instEmplTrainings = result.instEmplTrainings;
                if($scope.instEmplTrainings == null || $scope.instEmplTrainings.length < 1){
                    $scope.instEmplTrainings= [{}];
                }
                $scope.instEmplRecruitInfo = result.instEmplRecruitInfo;
                $scope.instEmpSpouseInfo = result.instEmpSpouseInfo;
                $scope.EducationalQualifications = result.instEmpEduQualis;
                if( $scope.EducationalQualifications == null || $scope.EducationalQualifications.length < 1){

                    $scope.EducationalQualifications = [{}];
                }else{
                    console.log($scope.EducationalQualifications);
                }
                console.log(result);


            });
        });
        $scope.previewImage = function (content, contentType, name)
        {
            var blob = $rootScope.b64toBlob(content, contentType);
            $rootScope.viewerObject.content = (window.URL || window.webkitURL).createObjectURL(blob);
            $rootScope.viewerObject.contentType = contentType;
            //$rootScope.viewerObject.pageTitle = "Birth Certificate  Image of "+name;
            $rootScope.viewerObject.pageTitle = name;
            // call the modal
            $rootScope.showFilePreviewModal();
        };
    }])
    .controller('InstEmployeeDeclineController',
    ['$scope', '$stateParams', '$modalInstance', '$state', 'entity','InstEmployeeDecline',
    function($scope, $stateParams, $modalInstance, $state, entity,InstEmployeeDecline) {
            $scope.decline = function(){

            };

            $scope.confirmApprove = function(){
                InstEmployeeDecline.update({id: $stateParams.id},$scope.causeDeny,function(result){
                    $modalInstance.close();
                    $state.go('instituteInfo.PendingEmployeeList',{},{reload:true});
                });
            }
            $scope.clear = function() {
                $modalInstance.close();
            };
    }])
    .controller('InstEmployeeConfirmApproveController',
    ['$scope', '$stateParams', '$modalInstance', '$state', 'entity','InstEmployeeApprove',
    function($scope, $stateParams, $modalInstance, $state, entity,InstEmployeeApprove) {
            $scope.decline = function(){

            };

        var onSaveSuccess = function () {

            console.log(result);

        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };
            $scope.confirmApprove = function(){
                console.log($stateParams.id);
                InstEmployeeApprove.approve({id: $stateParams.id},{},function(){
                    $state.go('instituteInfo.PendingEmployeeList',{},{reload:true});
                    $modalInstance.close();

                });

            }
            console.log($stateParams);
            $scope.clear = function() {
                $modalInstance.close();
            };
    }])
    .controller('InstEmployeeEligibleController',
    ['$scope', '$stateParams', '$modalInstance', '$state', 'entity','InstEmployeeEligible',
    function($scope, $stateParams, $modalInstance, $state, entity,InstEmployeeEligible) {
            $scope.decline = function(){

            };
            $scope.confirmApprove = function(){
                console.log($stateParams);
                InstEmployeeEligible.update({id: $stateParams.id},{},function(){
                    $modalInstance.close();
                    $state.go('instituteInfo.employeeList',{},{reload:true});
                });
            }
            console.log($stateParams.id);
            $scope.clear = function() {
                $modalInstance.close();
            };
    }]);
