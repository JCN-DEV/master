'use strict';

angular.module('stepApp')
    .controller('MPODetailsController',
    ['$scope', '$state', '$modal', 'entity', 'Employee','AttachmentByEmployeeAndName', 'ApplicantEducationEmployee', 'TrainingEmployee', 'ParseLinks','InstEmployeeCode','MpoApplication','$stateParams','AttachmentEmployee','Principal','MpoApplicationCheck', 'MpoApplicationLogEmployeeCode','AttachmentByName','$rootScope','AllInstInfraInfo', 'InstituteAllInfo',
    function ($scope, $state, $modal, entity, Employee,AttachmentByEmployeeAndName, ApplicantEducationEmployee, TrainingEmployee, ParseLinks,InstEmployeeCode,MpoApplication,$stateParams,AttachmentEmployee,Principal,MpoApplicationCheck, MpoApplicationLogEmployeeCode,AttachmentByName,$rootScope, AllInstInfraInfo, InstituteAllInfo) {
        $scope.url = null;
        $scope.employee = entity;
        console.log('>>>>>>>>>>>>>>>>>>>>>>>>>>');
        console.log(entity);
        $scope.isCollapsed = false;
        $scope.showApproveButton=false;
        $scope.showApplicationButtion=true;
        $scope.showApprove=false;
        $scope.finalApproved=false;
//TODO:change return value on MpoApplicationStatusType.java
        $scope.checkStatus = function(){
            if(Principal.hasAnyAuthority(['ROLE_INSTEMP'])){
                return 1;
            }else if(Principal.hasAnyAuthority(['ROLE_INSTITUTE'])){
                return 2;
            }else if(Principal.hasAnyAuthority(['ROLE_MANEGINGCOMMITTEE'])){
                return 3;
            }else if(Principal.hasAnyAuthority(['ROLE_DEO'])){
                return 4;
            }else if(Principal.hasAnyAuthority(['ROLE_FRONTDESK'])){
                return 5;
            }else if(Principal.hasAnyAuthority(['ROLE_AD'])){
                return 6;
            }else if(Principal.hasAnyAuthority(['ROLE_DIRECTOR'])){
                return 8;
            }else if(Principal.hasAnyAuthority(['ROLE_DG'])){
                return 9;
            }else if(Principal.hasAnyAuthority(['ROLE_MPOCOMMITTEE'])){
                return 10;
            }else{
                return 0;
            }
        };
        $scope.previewImage = function (content, contentType,name) {
            console.log('click-----');
            var blob = $rootScope.b64toBlob(content, contentType);
            $rootScope.viewerObject.content = (window.URL || window.webkitURL).createObjectURL(blob);
            $rootScope.viewerObject.contentType = contentType;
            $rootScope.viewerObject.pageTitle = name;
            // call the modal
            $rootScope.showFilePreviewModal();
        };
        if(Principal.hasAnyAuthority(['ROLE_INSTEMP'])){
            Principal.identity().then(function (account) {
                $scope.account = account;
                InstEmployeeCode.get({'code':$scope.account.login},function(res){
                    $scope.employee = res.instEmployee;

                    MpoApplicationCheck.get({'code':$scope.employee.code}, function(result) {

                        console.log(result);
                        if(result.id>0){
                            $scope.showApplicationButtion=false;
                        }else{
                            $rootScope.setErrorMessage('Your Mpo Application is not Complete yet.Please CompleteYour Application First.');
                            $state.go('mpo.application',{},{reload:true});
                        }

                        $scope.employee = res.instEmployee;
                        $scope.employee.id = res.instEmployee.id;
                        $scope.address = res.instEmpAddress;
                        //$scope.applicantEducation = res.instEmpEduQualisList;
                        //console.log($scope.applicantEducation);
                        $scope.applicantEducation = res.instEmpEduQualis;
                        $scope.instEmplExperiences = res.instEmplExperiences;
                        console.log($scope.applicantEducation);
                        $scope.applicantTraining = res.instEmplTrainings;
                        $scope.instEmplRecruitInfo = res.instEmplRecruitInfo;
                        $scope.instEmplBankInfo = res.instEmplBankInfo;
                        console.log($scope.applicantTraining);

                       /* AttachmentEmployee.query({id: $scope.employee.id}, function(result){
                            $scope.attachments = result;

                        });*/

                        AttachmentByEmployeeAndName.query({id: $scope.employee.id, applicationName:"MPO-Enlisting"}, function(result){
                            console.log(result);
                            $scope.attachments = result;

                        });


                    });

                });
            });
        }else{

            MpoApplication.get({id:$stateParams.id}, function(result) {
                console.log('result found');
                console.log(result);
               /* console.log($scope.checkStatus());*/
                console.log(result.status);
                $scope.mpoId = result.id;
                if(result.status > 5 && result.status < 10){
                    console.log('lkasdfklsdf>>>>>>>>>>><<<<<<<>>>>>>');
                    $scope.showApprove = true;

                }
                if(result.status > 9){
                    $scope.finalApproved = true;

                }
                if($stateParams.id>0){
                    $scope.showApplicationButtion=false;
                }
                console.log("keo amare mairala");
                console.log("checkStatus :"+$scope.checkStatus());
                console.log("resStatus :"+result.status);
                if( $scope.checkStatus()!=0 & $scope.checkStatus() == result.status){

                    $scope.showApproveButton=true;
                    $scope.mpoId = result.id;
                    console.log($scope.showApproveButton);
                }else{
                    $scope.showApproveButton=false;
                }

                InstEmployeeCode.get({'code':result.instEmployee.code},function(res){
                    console.log("lkajsdlfja;slkdjf;alksdjf;alskdjflkajsdf;lkj");
                    console.log(res);
                    $scope.employee = res.instEmployee;
                    $scope.employee.id = res.instEmployee.id;
                    $scope.address = res.instEmpAddress;
                    //$scope.applicantEducation = res.instEmpEduQualis;
                    $scope.instEmplExperiences = res.instEmplExperiences;
                    $scope.applicantEducation = res.instEmpEduQualis;
                    console.log($scope.applicantEducation);
                    $scope.applicantTraining = res.instEmplTrainings;
                    $scope.instEmplRecruitInfo = res.instEmplRecruitInfo;
                    $scope.instEmplBankInfo = res.instEmplBankInfo;

                    console.log($scope.applicantTraining);
                    /*AttachmentEmployee.query({id: $scope.employee.id}, function(result){
                        $scope.attachments = result;

                    });*/
                    AttachmentByEmployeeAndName.query({id: $scope.employee.id, applicationName:"MPO-Enlisting"}, function(result){
                        console.log(result);
                        $scope.attachments = result;

                    });
                    MpoApplicationLogEmployeeCode.get({'code':$scope.employee.code}, function(result) {
                        console.log(result);
                        $scope.mpoApplicationlogs = result;
                    });
                });

            });
        }

        $scope.profile = function(){


        };

        $scope.instituteInformation = function(){
            console.log('INstitute tab selected');
            AllInstInfraInfo.get({institueid: $scope.employee.institute.id},function(result){
                console.log(result);
                $scope.instShopInfos = result.instShopInfoList;
                $scope.instLabInfos = result.instLabInfoList;
                $scope.instPlayGroundInfos = result.instPlayGroundInfoList;
            });

            InstituteAllInfo.get({id: $scope.employee.institute.id}, function (res) {
                    console.log(res);
                    $scope.instGenInfo = res.instGenInfo;
                    $scope.instAdmInfo = res.instAdmInfo;
                    $scope.insAcademicInfo = res.insAcademicInfo;
                    $scope.instEmpCount = res.instEmpCount;
                    $scope.instFinancialInfos = res.instFinancialInfo;
                    $scope.instInfraInfo = res.instInfraInfo;
                    $scope.instGovernBodys = res.instGovernBody;
                    $scope.instCurriculums = res.instCurriculums;
                    $scope.instCources = res.instCourses;
                    $scope.instituteAllInfo = res;
                });


        };


        $scope.educations = function(){
            $scope.applicantEducations = $scope.applicantEducation;
            console.log($scope.applicanteducations);
        };


        $scope.training = function(){
           // $scope.trainings = applicantTraining;
            //console.log($scope.trainings);
        };
        $scope.attachment = function(){
            console.log($scope.employee.id);

        };

        $scope.displayAttachment= function(applicaiton) {

        }

        function b64toBlob(b64Data, contentType, sliceSize) {
            contentType = contentType || '';
            sliceSize = sliceSize || 512;

            var byteCharacters = atob(b64Data);
            var byteArrays = [];

            for (var offset = 0; offset < byteCharacters.length; offset += sliceSize) {
                var slice = byteCharacters.slice(offset, offset + sliceSize);

                var byteNumbers = new Array(slice.length);
                for (var i = 0; i < slice.length; i++) {
                    byteNumbers[i] = slice.charCodeAt(i);
                }

                var byteArray = new Uint8Array(byteNumbers);

                byteArrays.push(byteArray);
            }

            var blob = new Blob(byteArrays, {type: contentType});
            return blob;
        }

    }])
    .controller('TimescaleDetailsController',
    ['$scope', '$state', '$modal', 'entity', 'AttachmentByEmployeeAndName','Employee', 'TimeScaleApplication', 'ApplicantEducationEmployee', 'TrainingEmployee', 'ParseLinks','InstEmployeeCode','MpoApplication','$stateParams','AttachmentEmployee','Principal','MpoApplicationCheck', 'MpoApplicationLogEmployeeCode','AttachmentByName','$rootScope','TimeScaleApplicationCheck',
    function ($scope, $state, $modal, entity, AttachmentByEmployeeAndName,Employee, TimeScaleApplication, ApplicantEducationEmployee, TrainingEmployee, ParseLinks,InstEmployeeCode,MpoApplication,$stateParams,AttachmentEmployee,Principal,MpoApplicationCheck, MpoApplicationLogEmployeeCode,AttachmentByName,$rootScope,TimeScaleApplicationCheck) {
        $scope.url = null;
        $scope.employee = entity;
        $scope.isCollapsed = false;
        $scope.showApproveButton=false;
        $scope.showApplicationButtion=true;

        $scope.checkStatus = function(){
            if(Principal.hasAnyAuthority(['ROLE_INSTEMP'])){
                return 1;
            }else if(Principal.hasAnyAuthority(['ROLE_INSTITUTE'])){
                return 2;
            }else if(Principal.hasAnyAuthority(['ROLE_MANEGINGCOMMITTEE'])){
                return 3;
            }else if(Principal.hasAnyAuthority(['ROLE_DEO'])){
                return 4;
            }else if(Principal.hasAnyAuthority(['ROLE_FRONTDESK'])){
                return 5;
            }else if(Principal.hasAnyAuthority(['ROLE_AD'])){
                return 6;
            }else if(Principal.hasAnyAuthority(['ROLE_DIRECTOR'])){
                return 7;
            }else if(Principal.hasAnyAuthority(['ROLE_DG'])){
                return 8;
            }else if(Principal.hasAnyAuthority(['ROLE_MPOCOMMITTEE'])){
                return 9;
            }else{
                return 0;
            }
        };
        if(Principal.hasAnyAuthority(['ROLE_INSTEMP'])){
            console.log('comes to if');
            Principal.identity().then(function (account) {
                $scope.account = account;
                TimeScaleApplicationCheck.get({'code': $scope.account.login}, function (res) {
                    console.log(res);
                    $scope.timeScaleApplication = res;
                     if (res.id==0) {
                     console.log('application not found');
                         $rootScope.setErrorMessage('Your TimeScale Application Yet Not Complete. Please Complete your Application Procedure');
                     //$scope.showApplicationButtion = true;
                     // $state.go('mpo.employeeTrack', {}, {reload: true});
                     $state.go('mpo.application');
                     }
                });
                InstEmployeeCode.get({'code':$scope.account.login},function(res){
                    $scope.employee = res.instEmployee;

                    MpoApplicationCheck.get({'code':$scope.employee.code}, function(result) {

                        console.log(result);
                        if(result.id>0){
                            $scope.showApplicationButtion=false;
                        }

                        $scope.employee = res.instEmployee;
                        $scope.employee.id = res.instEmployee.id;
                        $scope.address = res.instEmpAddress;
                        //$scope.applicantEducation = res.instEmpEduQualis;
                        $scope.applicantEducation = res.instEmpEduQualis;
                        console.log($scope.applicantEducation);
                        $scope.applicantTraining = res.instEmplTrainings;
                        $scope.instEmplRecruitInfo = res.instEmplRecruitInfo;
                        $scope.instEmplBankInfo = res.instEmplBankInfo;
                        console.log($scope.applicantTraining);

                        AttachmentByEmployeeAndName.query({id: $scope.employee.id, applicationName:"Timescale"}, function(result){
                            console.log(result);
                            $scope.attachments = result;

                        });


                    });

                });
            });
        }else{
            console.log('comes to else');
            console.log('id :'+$stateParams.id);

            TimeScaleApplication.get({id:$stateParams.id}, function(result) {
                console.log(result);
                $scope.timeScaleApplication = result;
                //this line increasing the status value of result
                //console.log('checking status code: '+$scope.checkStatus());
                console.log(result.status);
                if($stateParams.id>0){
                    $scope.showApplicationButtion=false;
                }
               /* console.log('check :'+$scope.checkStatus()+' result: '+result.status);*/
                //TODO: change method like mpo application
                //used for temp testing
                if( $scope.checkStatus()!=0 & $scope.checkStatus() == result.status){

                    $scope.showApproveButton=true;
                    $scope.timescaleId = result.id;
                    console.log($scope.showApproveButton);
                    console.log('show approve true');
                }else{
                    $scope.showApproveButton=false;
                    console.log('shoq approve else');
                }

                InstEmployeeCode.get({'code':result.instEmployee.code},function(res){
                    console.log(res);
                    $scope.employee = res.instEmployee;
                    $scope.employee.id = res.instEmployee.id;
                    $scope.address = res.instEmpAddress;
                   // $scope.applicantEducation = res.instEmpEduQualis;
                    $scope.applicantEducation = res.instEmpEduQualis;
                    console.log($scope.applicantEducation);
                    $scope.applicantTraining = res.instEmplTrainings;
                    $scope.instEmplRecruitInfo = res.instEmplRecruitInfo;
                    $scope.instEmplBankInfo = res.instEmplBankInfo;
                    console.log($scope.applicantTraining);
                    /*AttachmentEmployee.query({id: $scope.employee.id}, function(result){
                        $scope.attachments = result;

                    });*/
                    AttachmentByEmployeeAndName.query({id: $scope.employee.id, applicationName:"Timescale"}, function(result){
                        console.log('-------------');
                        console.log(result);
                        $scope.attachments = result;
                       /* angular.forEach($scope.attachments, function (value, key) {
                            var blob = $rootScope.b64toBlob(value.file, value.fileContentType);
                            $scope.downloadsfile[index].url = (window.URL || window.webkitURL).createObjectURL( blob );
                        });*/

                    });
                    MpoApplicationLogEmployeeCode.get({'code':$scope.employee.code}, function(result) {
                        console.log(result);
                        $scope.mpoApplicationlogs = result;
                    });
                });

            });
        }
        $scope.profile = function(){


        };


        $scope.educations = function(){
            $scope.applicantEducations = $scope.applicantEducation;
            console.log($scope.applicanteducations);
        };


        $scope.training = function(){
            // $scope.trainings = applicantTraining;
            //console.log($scope.trainings);
        };
        $scope.attachment = function(){
            console.log($scope.employee.id);
          /*  AttachmentByEmployeeAndName.query({id: $scope.employee.id, applicationName:"Timescale"}, function(result){
                console.log(result);
                $scope.attachments = result;

            });*/
        };

        $scope.displayAttachment= function(applicaiton) {

            AttachmentByName.get({id:applicaiton.id},applicaiton,function(response){
                //console.log(response);
                var blob = b64toBlob(response.file, applicaiton.fileContentType);
                $scope.url = (window.URL || window.webkitURL).createObjectURL( blob );
                //console.log(applicaiton);
            });
            /* var blob = b64toBlob(applicaiton.file, applicaiton.fileContentType);
             $scope.url = (window.URL || window.webkitURL).createObjectURL( blob );
             console.log(applicaiton);*/
        }

        function b64toBlob(b64Data, contentType, sliceSize) {
            contentType = contentType || '';
            sliceSize = sliceSize || 512;

            var byteCharacters = atob(b64Data);
            var byteArrays = [];

            for (var offset = 0; offset < byteCharacters.length; offset += sliceSize) {
                var slice = byteCharacters.slice(offset, offset + sliceSize);

                var byteNumbers = new Array(slice.length);
                for (var i = 0; i < slice.length; i++) {
                    byteNumbers[i] = slice.charCodeAt(i);
                }

                var byteArray = new Uint8Array(byteNumbers);

                byteArrays.push(byteArray);
            }

            var blob = new Blob(byteArrays, {type: contentType});
            return blob;
        }
        $scope.previewImage = function (content, contentType,name) {
            console.log('click-----');
            var blob = $rootScope.b64toBlob(content, contentType);
            $rootScope.viewerObject.content = (window.URL || window.webkitURL).createObjectURL(blob);
            $rootScope.viewerObject.contentType = contentType;
            $rootScope.viewerObject.pageTitle = name;
            // call the modal
            $rootScope.showFilePreviewModal();
        };
    }]);
