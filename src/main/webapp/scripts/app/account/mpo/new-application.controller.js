'use strict';

angular.module('stepApp').controller('NewApplicationController',
    ['$scope', '$stateParams', '$parse', '$state', 'entity', 'Auth', 'Principal','$rootScope','AttachmentByEmployeeAndName','CurrentInstEmployee', 'Employee', 'User', 'Institute', 'ApplicantEducation', 'Training', 'AttachmentCategoryModule', 'Attachment', 'MpoApplication', 'InstEmployeeCode','InstEmployeeAddressInstitute', 'AttachmentEmployee','MpoApplicationCheck','AttachmentCategoryByApplicationName','AttachmentCatByAppNameAndDesignation',
    function ($scope, $stateParams, $parse, $state, entity, Auth, Principal,$rootScope,AttachmentByEmployeeAndName,CurrentInstEmployee, Employee, User, Institute, ApplicantEducation, Training, AttachmentCategoryModule, Attachment, MpoApplication, InstEmployeeCode,InstEmployeeAddressInstitute, AttachmentEmployee,MpoApplicationCheck,AttachmentCategoryByApplicationName,AttachmentCatByAppNameAndDesignation) {

        $scope.educationName = ['SSC/Dakhil', 'SSC (Vocational)', 'HSC/Alim', 'HSC (Vocational)', 'HSC (BM)', 'Honors/Degree', 'Masters'];
        $scope.currentSection = 'personal';
        $scope.applicantEducation = [];
        $scope.applicantTraining = [];
        $scope.applicantAttachment = [];
        $scope.applicantEducationCount = [0];
        $scope.applicantAttachmentCount = [0];
        $scope.applicantTrainingCount = [0];
        $scope.attachmentCategories = [];
        $scope.applicantTraining[0]={};
        $scope.applicantTraining[0].gpa = '';
        $scope.applicantEducation[0] ={};
        $scope.applicantEducation[0].examYear = '';
        $scope.attachmentList = [];
        $scope.mpoAttachmentType ='';
        $scope.mpoApplication ={};
        $scope.search = {};
        $scope.mpoForm = false;
        $scope.showAddMoreButton = false;
        $scope.currentSelectItem = [];
        $scope.mpoFormHasError = true;
        $scope.duplicateError = false;
        $scope.mpoFormEditMode = false;

        CurrentInstEmployee.get({},function(res){
            console.log();
            AttachmentCatByAppNameAndDesignation.query({name:'MPO-Enlisting', designationId:res.designationSetup.id}, function (result){
                console.log('*********************'+result.length);
                console.log(result);

                $scope.attachmentCategoryList = result;
            });
            if(res.mpoAppStatus == 2){
                console.log("Eligible to apply");
            }else if(res.mpoAppStatus == 1){
                console.log("apply after decline");
                AttachmentByEmployeeAndName.query({id: res.id, applicationName:"MPO-Enlisting"}, function(result){
                    console.log(result);

                    /*1372744*/
                    /*var i=0;
                    $scope.applicantAttachment.push({attachment:result[i].attachmentCategory});
                    for(i=1 ;i<result.length;i++){
                        $scope.applicantAttachmentCount.push(i);
                        $scope.applicantAttachment.push({attachment:result[i].attachmentCategory});
                    }*/

                    $scope.mpoFormHasError = false;
                    $scope.mpoFormEditMode = true;


                });
                /*MpoApplicationCheck.get({'code':$scope.account.login},function(res){
                    if(res.id > 0){
                        $scope.showApplicationButtion = true;
                        $state.go('mpo.employeeTrack',{},{reload:true});
                    }
                });*/
            }else if(res.mpoAppStatus < 1){
                console.log("Eligible to apply");
                $rootScope.setErrorMessage('You are not eligible to apply. Contact with your institute admin.');
                $state.go('employeeInfo.personalInfo');
            }else{
                $rootScope.setErrorMessage('You have applied already for this Application. Contact with your institute admin.');
                console.log("already applied!");
            }

        });
        $scope.calendar = {
            opened: {},
            dateFormat: 'yyyy-MM-dd',
            dateOptions: {},
            open: function ($event, which) {
                $event.preventDefault();
                $event.stopPropagation();
                $scope.calendar.opened[which] = true;
            }
        };
        $scope.employee = entity;

        /*AttachmentCategoryModule.query({id: 1}, function(result) {
            $scope.attachmentCategoryList = result;
        });*/
        /*AttachmentCategoryByApplicationName.get({name: 'MPO-Enlisting'}, function (result) {
            $scope.attachmentCategoryList = result;
        });*/

        Principal.identity().then(function (account) {
            $scope.account = account;

            if($scope.isInArray('ROLE_ADMIN', $scope.account.authorities))
            {
                $scope.showTimeScaleForm();
            }
            else if($scope.isInArray('ROLE_INSTITUTE', $scope.account.authorities))
            {
                $scope.showTimeScaleForm();
            }
            else if($scope.isInArray('ROLE_INSTEMP', $scope.account.authorities))
            {
                $scope.search.code = $scope.account.login;
                $scope.showTimeScaleForm();
            }

        });


        $scope.isInArray = function isInArray(value, array) {
            return array.indexOf(value) > -1;
        };

        $scope.load = function (id) {
            Employee.get({id: id}, function (result) {
                $scope.employee = result;
            });
        };

        $scope.save = function () {
            MpoApplicationCheck.get({'code':$scope.account.login},function(res){
                if(res.status > 2){
                    //$scope.showApplicationButtion = true;
                    $state.go('mpo.employeeTrack',{},{reload:true});
                }
            });
            //mpoApplication entry
            $scope.isSaving = true;
            $scope.mpoApplication.name = 'MPO-Enlisting';
            $scope.mpoApplication.mpoApplicationDate = new Date();
            $scope.mpoApplication.status = 1;
            $scope.mpoApplication.instEmployee = $scope.employee;
            MpoApplication.save($scope.mpoApplication);

            //employee attachment
            angular.forEach($scope.applicantAttachmentCount, function (value, key) {
                console.log('-----');
                if ($scope.applicantAttachment[value].attachment != '') {
                    $scope.applicantAttachment[value].instEmployee = {};
                    $scope.applicantAttachment[value].name = $scope.applicantAttachment[value].attachmentCategory.attachmentName;
                    $scope.applicantAttachment[value].instEmployee.id = $scope.employee.id;
                    Attachment.save($scope.applicantAttachment[value]);
                }
            });
            //$scope.$emit('stepApp:mpoApplicationUpdate', result);
            $rootScope.setSuccessMessage('Application Submitted Successfully.');
            $scope.isSaving = false;
            $state.go('mpo.employeeTrack',{},{reload:true});
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.showTimeScaleForm = function () {
            InstEmployeeCode.get({'code':$scope.search.code},function(res){
                console.log(res);
                $scope.employee = res.instEmployee;


                if((res.instEmployee.category === 'Teacher' && res.instEmpEduQualis.length < 2) || res.instEmpAddress == null || res.instEmplRecruitInfo == null || res.instEmplBankInfo == null){


                    //recruit information is mandatory for mpo application
                    if(res.instEmplRecruitInfo == null){
                        $rootScope.setErrorMessage('Enrollment Information not found! Please, Complete your Enrollment Information.');
                        $state.go('employeeInfo.recruitmentInfo');
                    }



                    //minimum 2 certificate exam is needed for teacher
                    if(res.instEmployee.category === 'Teacher' && res.instEmpEduQualis.length < 2){
                        $rootScope.setErrorMessage('Educational Qualifications not found! Please, Complete your educational qualifications.');
                        $state.go('employeeInfo.educationalHistory');
                    }

                    //personal information is mandatory for mpo application
                    if(res.instEmpAddress == null || res.instEmplBankInfo == null){
                        $rootScope.setErrorMessage('Personal Information not found! Please, Complete your Personal Information.');
                        $state.go('employeeInfo.personalInfo');
                    }
                }

                if(res.instEmployee.mpoAppStatus == 2){
                    console.log("Eligible to apply");
                }else if(res.instEmployee.mpoAppStatus == 1){
                    console.log("apply after decline");

                }else if(res.instEmployee.mpoAppStatus < 1){
                    $rootScope.setErrorMessage('You are not eligible to apply. Contact with your institute admin.');
                    $state.go('employeeInfo.personalInfo');
                }else{
                    console.log("already applied!");
                    $state.go('mpo.employeeTrack');

                }

                console.log($scope.employee);
                $scope.address = res.instEmpAddress;

                $scope.applicantEducation = res.instEmpEduQualis;
                console.log($scope.applicantEducation);
                $scope.applicantTraining = res.instEmplTrainings;
                console.log($scope.applicantTraining);
                $scope.instEmplRecruitInfo = res.instEmplRecruitInfo;
                $scope.instEmplBankInfo = res.instEmplBankInfo;
                console.log("==============================");
                console.log($scope.address);
                console.log($scope.instEmplBankInfo);
                console.log("==============================");
                $scope.mpoForm = true;
            });
        };

        $scope.hideMpoForm = function () {
            $scope.mpoForm = false;
        };

        $scope.mpoNext = function (current, next) {
            $scope.currentSection = next;
            $scope.prevSection = current;
        }
        $scope.mpoPrev = function (current, prev) {
            $scope.currentSection = prev;
            $scope.nextSection = current;
        }
        $scope.addMoreEducation = function () {
            $scope.applicantEducationCount.push($scope.applicantEducationCount.length);
            $scope.applicantEducation[$scope.applicantEducationCount.length].examYear = '';

        }
        $scope.addMoreAttachment = function () {
            //removeItem
            if(!inArray($scope.applicantAttachmentCount.length, $scope.applicantAttachmentCount)) {
                $scope.applicantAttachmentCount.push($scope.applicantAttachmentCount.length);
            } else {
                $scope.applicantAttachmentCount.length++;
                $scope.applicantAttachmentCount.push($scope.applicantAttachmentCount.length);
            }

            $scope.showAddMoreButton = false;
        }
        $scope.removeAttachment = function(attachment) {
            $scope.showAddMoreButton = true;
            $scope.mpoFormHasError = true;
            var index =  $scope.applicantAttachmentCount.indexOf(attachment);
            $scope.applicantAttachmentCount.splice(index,1);

            if($scope.applicantAttachmentCount.length == $scope.attachmentCategoryList.length-1) {
                $scope.showAddMoreButton = false;
                $scope.mpoFormHasError = false;
            }
        }
        $scope.addMoreTraining = function () {
            $scope.applicantTrainingCount.push($scope.applicantTrainingCount.length);
            $scope.applicantTraining[$scope.applicantTrainingCount.length].gpa = '';
        }

        $scope.educationHtml = function () {

        }

        $scope.setAttachment = function($index, attachment,noAttach) {
            console.log(attachment);
            if(noAttach ){
                if($scope.applicantAttachmentCount.length == $scope.attachmentCategoryList.length){
                    $scope.showAddMoreButton = false;
                }else{
                    $scope.showAddMoreButton = true;
                }

                if($scope.mpoFormEditMode) $scope.mpoFormHasError = false;

            }
            try{
                /*$scope.showAddMoreButton = true;*/
                if(attachment==""){
                    $scope.mpoFormHasError = true;
                }else{
                    if(!noAttach && attachment.file)
                        $scope.showAddMoreButton = true;
                    if(noAttach && (attachment.remarks == undefined || attachment.remarks==""))
                        $scope.showAddMoreButton = true;
                }
                attachment.attachmentCategory = angular.fromJson(attachment.attachment);

                if($scope.applicantAttachmentCount.length == $scope.attachmentCategoryList.length){
                    $scope.showAddMoreButton = false;
                }

                if(attachment.attachmentCategory.id)
                {
                    $scope.currentSelectItem[$index] = attachment.attachmentCategory.id;
                }
                if($scope.mpoFormEditMode) $scope.mpoFormHasError = false;

            }catch(e) {
                $scope.showAddMoreButton = false;
                $scope.mpoFormHasError = true;
                $scope.currentSelectItem.splice($index, 1);
            }

            //if($scope.attachmentCategoryList.length-1 == arrayUnique($scope.currentSelectItem).length){
            if($scope.attachmentCategoryList.length == arrayUnique($scope.currentSelectItem).length){
                $scope.mpoFormHasError = false;
                angular.forEach($scope.applicantAttachment, function (value, key) {
                    console.log(value);

                    if (value.noAttachment && (value.remarks == undefined || value.remarks=="")) {
                        $scope.mpoFormHasError = true;
                    }else {
                        if (value.fileName) {
                            $scope.mpoFormHasError = true;
                        }
                    }

                });
            }
            else{
                angular.forEach($scope.applicantAttachment, function (value, key) {
                    console.log(value);

                    if (!value.noAttachment && (value.fileName)) {
                        $scope.mpoFormHasError = true;

                    }
                });
               /* $scope.mpoFormHasError = true;*/
            }

            if($scope.mpoFormEditMode) $scope.mpoFormHasError = false;

            if(arrayUnique($scope.currentSelectItem).length != $scope.currentSelectItem.length)
                $scope.duplicateError = true;
            else
                $scope.duplicateError = false;

            if($scope.duplicateError){
                $scope.showAddMoreButton = false;
            }
        }

        $scope.selectNoAttachment = function(val,val2,file)
        {
            console.log(val2);
            console.log($scope.attachmentCategoryList.length);
            console.log($scope.applicantAttachmentCount.length);
            if(val && val2){
               // if($scope.applicantAttachmentCount.length === $scope.attachmentCategoryList.length-1){
                if($scope.applicantAttachmentCount.length === $scope.attachmentCategoryList.length){

                    $scope.showAddMoreButton = false;
                    $scope.mpoFormHasError = false;
                    angular.forEach($scope.applicantAttachment, function (value, key) {

                            if (value.noAttachment && (value.remarks == undefined || value.remarks=="")) {
                                $scope.mpoFormHasError = true;
                            }
                    });

                }else{
                    $scope.showAddMoreButton = true;
                }
            }else{
                if(file==undefined){
                    $scope.mpoFormHasError = true;
                }

                if($scope.applicantAttachmentCount.length == $scope.attachmentCategoryList.length){
                    $scope.showAddMoreButton = false;
                }
                /*$scope.showAddMoreButton = false;*/
            }


            /*if($scope.applicantAttachmentCount.length === $scope.attachmentCategoryList.length-1){
                $scope.showAddMoreButton = false;
            }*/
            if($scope.applicantAttachmentCount.length === $scope.attachmentCategoryList.length){
                $scope.showAddMoreButton = false;
            }
        }
        $scope.remarksChange = function(val,val2)
        {
                //if($scope.applicantAttachmentCount.length === $scope.attachmentCategoryList.length-1){
                if($scope.applicantAttachmentCount.length === $scope.attachmentCategoryList.length){
                    $scope.showAddMoreButton = false;
                    $scope.mpoFormHasError = false;
                    angular.forEach($scope.applicantAttachment, function (value, key) {

                        if (value.noAttachment && (value.remarks == undefined || value.remarks=="")) {
                            $scope.mpoFormHasError = true;
                        }
                    });

                }

                if($scope.mpoFormEditMode) $scope.mpoFormHasError = false;
        }

        function inArray(needle, haystack) {
            var length = haystack.length;
            for(var i = 0; i < length; i++) {
                if(typeof haystack[i] == 'object') {
                    if(arrayCompare(haystack[i], needle)) return true;
                } else {
                    if(haystack[i] == needle) return true;
                }
            }
            return false;
        }

        function arrayUnique(a) {
            return a.reduce(function(p, c) {
                if (p.indexOf(c) < 0) p.push(c);
                return p;
            }, []);
        };

        function arrayCompare(a1, a2) {
            if (a1.length != a2.length) return false;
            var length = a2.length;
            for (var i = 0; i < length; i++) {
                if (a1[i] !== a2[i]) return false;
            }
            return true;
        }

        $scope.abbreviate = function (text) {
            if (!angular.isString(text)) {
                return '';
            }
            if (text.length < 30) {
                return text;
            }
            return text ? (text.substring(0, 15) + '...' + text.slice(-10)) : '';
        };

        $scope.byteSize = function (base64String) {
            if (!angular.isString(base64String)) {
                return '';
            }
            function endsWith(suffix, str) {
                return str.indexOf(suffix, str.length - suffix.length) !== -1;
            }
            function paddingSize(base64String) {
                if (endsWith('==', base64String)) {
                    return 2;
                }
                if (endsWith('=', base64String)) {
                    return 1;
                }
                return 0;
            }
            function size(base64String) {
                return base64String.length / 4 * 3 - paddingSize(base64String);
            }
            function formatAsBytes(size) {
                return size.toString().replace(/\B(?=(\d{3})+(?!\d))/g, " ") + " bytes";
            }

            return formatAsBytes(size(base64String));
        };

        $scope.setFile = function ($file, attachment) {
            $scope.showAddMoreButton = true;
            $scope.mpoFormHasError = true;
            try{
                if ($file) {
                    var fileReader = new FileReader();
                    fileReader.readAsDataURL($file);
                    fileReader.onload = function (e) {
                        console.log('--------------');
                        var base64Data = e.target.result.substr(e.target.result.indexOf('base64,') + 'base64,'.length);
                        $scope.$apply(function() {
                            try{
                                attachment.file = base64Data;
                                attachment.fileContentType = $file.type;
                                attachment.fileName = $file.name;
                            }catch(e) {
                                $scope.showAddMoreButton = false;
                            }
                        });
                    };

                    /*if($scope.applicantAttachmentCount.length == $scope.attachmentCategoryList.length-1){
                        $scope.showAddMoreButton = false;
                    }*/
                    if($scope.applicantAttachmentCount.length == $scope.attachmentCategoryList.length){
                        $scope.showAddMoreButton = false;
                    }

                    /*if($scope.attachmentCategoryList.length-1 == arrayUnique($scope.currentSelectItem).length)
                        $scope.mpoFormHasError = false;
                    else
                        $scope.mpoFormHasError = true;*/
                    if($scope.attachmentCategoryList.length == arrayUnique($scope.currentSelectItem).length)
                        $scope.mpoFormHasError = false;
                    else
                        $scope.mpoFormHasError = true;

                    if($scope.mpoFormEditMode) $scope.mpoFormHasError = false;
                }
            }catch(e) {
                $scope.showAddMoreButton = false;
                $scope.mpoFormHasError = true;

                if($scope.mpoFormEditMode) $scope.mpoFormHasError = false;
            }
        };


        $scope.previewImage = function (content, contentType,name)
        {
            var blob = $rootScope.b64toBlob(content, contentType);
            $rootScope.viewerObject.content = (window.URL || window.webkitURL).createObjectURL(blob);
            $rootScope.viewerObject.contentType = contentType;
            $rootScope.viewerObject.pageTitle = name;
            // call the modal
            $rootScope.showFilePreviewModal();
        };

        $scope.cancel = function () {
            $state.go('employeeInfo.dashboard');
        };


    }]);
