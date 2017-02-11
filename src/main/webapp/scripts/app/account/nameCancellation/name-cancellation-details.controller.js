'use strict';

angular.module('stepApp')
    .controller('NameCnclDetailsController',
    ['$scope', '$state', '$modal','NameCnclApplicationCheck', 'NameCnclApplicationLogEmployeeCode','BEdApplication','NameCnclApplication','entity','BEdApplicationCheck', 'Employee', 'ApplicantEducationEmployee', 'TrainingEmployee', 'ParseLinks','InstEmployeeCode','MpoApplication','$stateParams','AttachmentEmployee','Principal','MpoApplicationCheck', 'MpoApplicationLogEmployeeCode','AttachmentByName','$rootScope','AttachmentByEmployeeAndName','ProfessorApplication','PrincipleApplicationCheck',
    function ($scope, $state, $modal,NameCnclApplicationCheck, NameCnclApplicationLogEmployeeCode,BEdApplication,NameCnclApplication,entity,BEdApplicationCheck, Employee, ApplicantEducationEmployee, TrainingEmployee, ParseLinks,InstEmployeeCode,MpoApplication,$stateParams,AttachmentEmployee,Principal,MpoApplicationCheck, MpoApplicationLogEmployeeCode,AttachmentByName,$rootScope,AttachmentByEmployeeAndName,ProfessorApplication,PrincipleApplicationCheck) {
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
                return 8;
            }else if(Principal.hasAnyAuthority(['ROLE_DG'])){
                return 9;
            }else if(Principal.hasAnyAuthority(['ROLE_MPOCOMMITTEE'])){
                return 10;
            }else{
                return 0;
            }
        };
        $scope.profile = function(){

            if(Principal.hasAnyAuthority(['ROLE_INSTEMP'])){
                Principal.identity().then(function (account) {
                    $scope.account = account;
                    NameCnclApplicationCheck.get({'code': $scope.account.login}, function (res) {
                        console.log(res);
                        if (res.id == 0) {
                            console.log('Application not completed');
                            $rootScope.setErrorMessage('You Name Cancellation Application Yet Not Complete. Please Complete your Application Procedure');
                            $state.go('mpo.application');
                        }
                    });
                    InstEmployeeCode.get({'code':$scope.account.login},function(res){
                        $scope.employee = res.instEmployee;

                        NameCnclApplicationCheck.get({'code':$scope.employee.code}, function(result) {
                            console.log(result);
                            if(result.id>0){
                                $scope.showApplicationButtion=false;
                            }
                            $scope.employee = res.instEmployee;

                            $scope.employee.id = res.instEmployee.id;
                            $scope.address = res.instEmpAddress;
                           // $scope.applicantEducation = res.instEmpEduQualis;
                            $scope.applicantEducation = res.instEmpEduQualisList;
                            console.log($scope.applicantEducation);
                            $scope.applicantTraining = res.instEmplTrainings;
                            $scope.instEmplRecruitInfo = res.instEmplRecruitInfo;
                            $scope.instEmplBankInfo = res.instEmplBankInfo;
                            console.log($scope.applicantTraining);

                            /*AttachmentEmployee.query({id: $scope.employee.id}, function(result){
                                $scope.attachments = result;

                            });*/
                            AttachmentByEmployeeAndName.query({id: $scope.employee.id, applicationName:"NameCancle"}, function(result){
                                console.log('-------------');
                                console.log(result);
                                $scope.attachments = result;

                            });
                           /* InstEmployeeCode.get({'code':result.instEmployee.code},function(res){
                                console.log(res);
                                $scope.employee = res.instEmployee;
                                $scope.employee.id = res.instEmployee.id;
                                $scope.address = res.instEmpAddress;
                                $scope.applicantEducation = res.instEmpEduQualis;
                                console.log($scope.applicantEducation);
                                $scope.applicantTraining = res.instEmplTrainings;
                                $scope.instEmplRecruitInfo = res.instEmplRecruitInfo;
                                $scope.instEmplBankInfo = res.instEmplBankInfo;
                                console.log($scope.applicantTraining);

                                AttachmentEmployee.query({id: $scope.employee.id}, function(result){
                                    $scope.attachments = result;

                                });
                            });*/

                        });

                    });
                });
            }else{

                NameCnclApplication.get({id:$stateParams.id}, function(result) {
                $scope.nameCnclApplication = result;
                console.log(result);
                console.log($scope.checkStatus());
                console.log(result.status);
                if($stateParams.id>0){
                    $scope.showApplicationButtion=false;
                }
                if( $scope.checkStatus()!=0 & $scope.checkStatus() ==result.status){

                    $scope.showApproveButton=true;
                    $scope.mpoId = result.id;
                    console.log($scope.showApproveButton);
                }else{
                    $scope.showApproveButton=false;
                }

                InstEmployeeCode.get({'code':result.instEmployee.code},function(res){
                    console.log(res);
                    $scope.employee = res.instEmployee;
                    $scope.employee.id = res.instEmployee.id;
                    $scope.address = res.instEmpAddress;
                   /* $scope.applicantEducation = res.instEmpEduQualis;*/
                    $scope.applicantEducation = res.instEmpEduQualisList;
                    console.log($scope.applicantEducation);
                    $scope.applicantTraining = res.instEmplTrainings;
                    $scope.instEmplRecruitInfo = res.instEmplRecruitInfo;
                    $scope.instEmplBankInfo = res.instEmplBankInfo;
                    console.log($scope.applicantTraining);
                    /*AttachmentEmployee.query({id: $scope.employee.id}, function(result){
                        $scope.attachments = result;

                    });*/
                    AttachmentByEmployeeAndName.query({id: $scope.employee.id, applicationName:"NameCancle"}, function(result){
                        console.log('-------------');
                        console.log(result);
                        $scope.attachments = result;

                    });
                    NameCnclApplicationLogEmployeeCode.get({'code':$scope.employee.code}, function(result) {
                        console.log(result);
                        $scope.mpoApplicationlogs = result;
                    });
                });

            });
            }
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
           /* AttachmentEmployee.query({id: $scope.employee.id}, function(result){
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
