angular.module('stepApp').controller('TimeScaleApplicationControllerss',
    ['$scope', '$stateParams', '$parse', '$state', 'entity', 'Auth','TimeScaleApplicationCheck', 'Principal','TimeScaleApplication', '$rootScope', 'CurrentInstEmployee', 'Employee', 'User', 'Institute', 'ApplicantEducation', 'Training', 'AttachmentCategoryModule', 'Attachment', 'MpoApplication', 'InstEmployeeCode', 'InstEmployeeAddressInstitute', 'AttachmentEmployee', 'MpoApplicationCheck','AttachmentCategoryByApplicationName','InstEmplRecruitInfoCurrent',
    function ($scope, $stateParams, $parse, $state, entity, Auth,TimeScaleApplicationCheck, Principal,TimeScaleApplication, $rootScope, CurrentInstEmployee, Employee, User, Institute, ApplicantEducation, Training, AttachmentCategoryModule, Attachment, MpoApplication, InstEmployeeCode, InstEmployeeAddressInstitute, AttachmentEmployee, MpoApplicationCheck,AttachmentCategoryByApplicationName,InstEmplRecruitInfoCurrent) {

        $scope.educationName = ['SSC/Dakhil', 'SSC (Vocational)', 'HSC/Alim', 'HSC (Vocational)', 'HSC (BM)', 'Honors/Degree', 'Masters'];
        $scope.currentSection = 'personal';
        $scope.applicantEducation = [];
        $scope.applicantTraining = [];
        $scope.applicantAttachment = [];
        $scope.applicantEducationCount = [0];
        $scope.applicantAttachmentCount = [0];
        $scope.applicantTrainingCount = [0];
        $scope.attachmentCategories = [];
        $scope.applicantTraining[0] = {};
        $scope.applicantTraining[0].gpa = '';
        $scope.applicantEducation[0] = {};
        $scope.applicantEducation[0].examYear = '';
        $scope.attachmentList = [];
        $scope.mpoAttachmentType = '';
        $scope.mpoApplication = {};
        $scope.search = {};
        $scope.mpoForm = false;
        $scope.showAddMoreButton = false;
        $scope.currentSelectItem = [];
        $scope.mpoFormHasError = true;
        $scope.duplicateError = false;
        $scope.timeScaleApplication = {};
        $scope.noDisciplinaryAction=false;
        $scope.noWorkingBreakClick=false;
        $scope.timeScaleApplication.disciplinaryAction=true;
        $scope.timeScaleApplication.workingBreak=true;
        //$scope.timeScaleApplication.disActionFile = {};
        //TODO: add all validation to back end
        //Conditions to apply for Assistant Professor
        /* 1. must be mpo enlisted
         2.work duration at least 8 years
         */

        CurrentInstEmployee.get({}, function (res) {
            console.log("result :"+res);
            $scope.employee = res;

            //checking mpo enlisted and mpo activation time is minimum 8 years
            if (res.mpoAppStatus == 5 && res.mpoActiveDate != null) {
                if(getYearDifferenceWithCurrentDate(res.mpoActiveDate) >= 8 ){
                    console.log('experience greter than 8 years');
                }else{
                    console.log('experience less than 8 years');
                    $state.go('employeeInfo.personalInfo');
                    $rootScope.setErrorMessage('You are not eligible to apply. You must have at least 8 years experience.');
                }

                //previous checking experience is 8 years with recruiting date
                /*InstEmplRecruitInfoCurrent.get({}, function(result){
                    console.log('experience :'+getYearDifferenceWithCurrentDate(result.recruitmentDate));
                    if(result.recruitmentDate){
                        //checking work experience at least 8 years
                        if(getYearDifferenceWithCurrentDate(result.recruitmentDate) >= 8 ){
                            console.log('experience greter than 8 years');
                        }else{
                            console.log('experience less than 8 years');
                            $state.go('employeeInfo.personalInfo');
                            $rootScope.setErrorMessage('You are not eligible to apply. You must have at least 8 years experience.');
                        }
                    }
                });*/
                console.log("Eligible to apply");
                //if not mpo enlisted
            } else if (res.mpoAppStatus < 5) {
                $state.go('employeeInfo.personalInfo');
                $rootScope.setErrorMessage('You are not eligible to apply. Contact with your institute admin.');
            } else {
                $state.go('employeeInfo.personalInfo');
                $rootScope.setErrorMessage('You are not eligible to apply. Contact with your institute admin.');
            }
        });

        function getYearDifferenceWithCurrentDate(dateString) {
            var today = new Date();
            var otherDate = new Date(dateString);
            var year = today.getFullYear() - otherDate.getFullYear();
            var m = today.getMonth() - otherDate.getMonth();
            if (m < 0 || (m === 0 && today.getDate() < otherDate.getDate())) {
                year--;
            }
            return year;
        }
        /* CurrentInstEmployee.get({},function(res){
         if(res.mpoAppStatus == 2){
         console.log("Eligible to apply");
         }else if(res.mpoAppStatus < 2){
         console.log("Eligible to apply");
         $rootScope.setErrorMessage('You are not eligible to apply. Contact with your institute admin.');
         $state.go('employeeInfo.personalInfo');
         }else{
         console.log("already applied!");
         }
         });*/

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
        CurrentInstEmployee.get({}, function (res) {
            console.log("result :"+res);
            $scope.employee = res;

        });

       /* AttachmentCategoryModule.query({id: 4827688}, function (result) {
            $scope.attachmentCategoryList = result;
        });*/
        AttachmentCategoryByApplicationName.get({name: 'Timescale'}, function (result) {
            $scope.attachmentCategoryList = result;
        });

        Principal.identity().then(function (account) {
            $scope.account = account;
            TimeScaleApplicationCheck.get({'code': $scope.account.login}, function (res) {

                if(res.id == 0){
                    console.log("no result found");
                }else{
                    if(res.status > 1){
                        $scope.showApplicationButtion = true;
                        // $state.go('mpo.employeeTrack', {}, {reload: true});
                        $state.go('mpo.timescaleApplicationStatus');
                    }
                }

                /*if(res.status > 1){
                    $scope.showApplicationButtion = true;
                    // $state.go('mpo.employeeTrack', {}, {reload: true});
                    $state.go('mpo.timescaleApplicationStatus');
                }else{
                    $scope.timeScaleApplication = res;
                }*/
                /*if (res.id > 0) {
                    console.log('already applied');
                    $scope.showApplicationButtion = true;
                    // $state.go('mpo.employeeTrack', {}, {reload: true});
                    $state.go('mpo.timescaleApplicationStatus');
                }*/
            });

            /*if ($scope.isInArray('ROLE_ADMIN', $scope.account.authorities)) {
                $scope.showTimeScaleForm();
            }
            else if ($scope.isInArray('ROLE_INSTITUTE', $scope.account.authorities)) {
                $scope.showTimeScaleForm();
            }
            else if ($scope.isInArray('ROLE_INSTEMP', $scope.account.authorities)) {
                $scope.search.code = $scope.account.login;
                $scope.showTimeScaleForm();
            }*/

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
            TimeScaleApplicationCheck.get({'code': $scope.account.login}, function (res) {
                console.log(res);
                if (res.status > 1) {
                    console.log('already applied');
                    $scope.showApplicationButtion = true;
                   // $state.go('mpo.employeeTrack', {}, {reload: true});
                    $state.go('mpo.timescaleApplicationStatus', {}, {reload: true});
                }
            });
            //mpoApplication entry
            $scope.isSaving = true;


            $scope.timeScaleApplication.instEmployee = $scope.employee;
            console.log($scope.timeScaleApplication);
            if($scope.timeScaleApplication.id != null){
                console.log("comes to update");
                TimeScaleApplication.update($scope.timeScaleApplication);
            }else{
                console.log("comes to save");
            TimeScaleApplication.save($scope.timeScaleApplication);
            }



            angular.forEach($scope.applicantAttachmentCount, function (value, key) {
                if ($scope.applicantAttachment[value].attachment != '') {
                    $scope.applicantAttachment[value].instEmployee = {};
                    $scope.applicantAttachment[value].name = $scope.applicantAttachment[value].attachmentCategory.attachmentName;
                    $scope.applicantAttachment[value].instEmployee = $scope.employee;
                    console.log('attachement>>>>>>');
                    console.log($scope.applicantAttachment[value]);
                    Attachment.save($scope.applicantAttachment[value]);
                }
                /*if ($scope.applicantAttachment[value].attachment != '') {
                 console.log('comes inside loop');
                 //console.log($scope.employee);
                 $scope.applicantAttachment[value].instEmployee = {};
                 $scope.applicantAttachment[value].name = $scope.applicantAttachment[value].attachmentCategory.attachmentName;
                 $scope.applicantAttachment[value].instEmployee = $scope.employee;
                 *//*console.log($scope.applicantAttachment[value].name);
                 console.log($scope.applicantAttachment[value].instEmployee.id);*//*
                 Attachment.save($scope.applicantAttachment[value]);
                 }*/
            });



            //$scope.$emit('stepApp:mpoApplicationUpdate', result);
            $scope.isSaving = false;
            $state.go('mpo.timescaleApplicationStatus', {}, {reload: true});
            $rootScope.setSuccessMessage('Application Submitted Successfully.');
            //console.log($scope.applicantAttachmentCount);
            //employee attachment

        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        /*var onSaveTimescaleSuccess = function (result) {
            angular.forEach($scope.applicantAttachmentCount, function (value, key) {
                if ($scope.applicantAttachment[value].attachment != '') {
                    console.log('comes inside loop');
                    $scope.applicantAttachment[value].instEmployee = {};
                    $scope.applicantAttachment[value].name = $scope.applicantAttachment[value].attachmentCategory.attachmentName;
                    $scope.applicantAttachment[value].instEmployee.id = $scope.employee.id;
                    console.log($scope.applicantAttachment[value].name);
                    console.log($scope.applicantAttachment[value].instEmployee.id);
                    Attachment.save($scope.applicantAttachment[value]);
                }
            });
            //$scope.$emit('stepApp:mpoApplicationUpdate', result);
            $scope.isSaving = false;
            $state.go('mpo.timescaleApplicationStatus', {}, {reload: true});
        };*/

        $scope.showTimeScaleForm = function () {
            InstEmployeeCode.get({'code': $scope.search.code}, function (res) {
                $scope.employee = res.instEmployee;
                $scope.address = res.instEmpAddress;
                /*$scope.applicantEducation = res.instEmpEduQualis;*/
                $scope.applicantEducation = res.instEmpEduQualisList;
                $scope.applicantTraining = res.instEmplTrainings;
                $scope.instEmplRecruitInfo = res.instEmplRecruitInfo;
                $scope.instEmplBankInfo = res.instEmplBankInfo;
                $scope.mpoForm = true;
            });
        };

        $scope.hideMpoForm = function () {
            $scope.mpoForm = false;
        };

        $scope.timescaleNext = function (current, next) {
            $scope.currentSection = next;
            $scope.prevSection = current;
        }
        $scope.timescalePrev = function (current, prev) {
            $scope.currentSection = prev;
            $scope.nextSection = current;
        }
        $scope.addMoreEducation = function () {
            $scope.applicantEducationCount.push($scope.applicantEducationCount.length);
            $scope.applicantEducation[$scope.applicantEducationCount.length].examYear = '';

        }
        $scope.addMoreAttachment = function () {
            //removeItem
            if (!inArray($scope.applicantAttachmentCount.length, $scope.applicantAttachmentCount)) {
                $scope.applicantAttachmentCount.push($scope.applicantAttachmentCount.length);
            } else {
                $scope.applicantAttachmentCount.length++;
                $scope.applicantAttachmentCount.push($scope.applicantAttachmentCount.length);
            }

            $scope.showAddMoreButton = false;
        }
        $scope.remarksChange = function(noAttachment,remarks)
        {
            console.log(noAttachment);
            console.log(remarks);
            //if($scope.applicantAttachmentCount.length === $scope.attachmentCategoryList.length-1){

            if($scope.applicantAttachmentCount.length === $scope.attachmentCategoryList.length){
                $scope.showAddMoreButton = false;
                $scope.mpoFormHasError = false;
                angular.forEach($scope.applicantAttachment, function (value, key) {

                    if (value.noAttachment && (value.remarks == undefined || value.remarks=="")) {
                        $scope.mpoFormHasError = true;
                    }
                });

            }else{
                if(noAttachment && !(remarks == undefined || remarks=="")){
                    $scope.showAddMoreButton = true;
                }else{
                    $scope.showAddMoreButton = false;
                }
            }
        }
        $scope.removeAttachment = function (attachment) {
            $scope.showAddMoreButton = true;
            $scope.mpoFormHasError = true;
            var index = $scope.applicantAttachmentCount.indexOf(attachment);
            $scope.applicantAttachmentCount.splice(index, 1);

            if ($scope.applicantAttachmentCount.length == $scope.attachmentCategoryList.length) {
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

            if(arrayUnique($scope.currentSelectItem).length != $scope.currentSelectItem.length)
                $scope.duplicateError = true;
            else
                $scope.duplicateError = false;

            if($scope.duplicateError){
                $scope.showAddMoreButton = false;
            }
        }
        /*$scope.setAttachment = function ($index, attachment) {
            try {
                $scope.showAddMoreButton = true;
                $scope.mpoFormHasError = true;
                attachment.attachmentCategory = angular.fromJson(attachment.attachment);

                if ($scope.applicantAttachmentCount.length == $scope.attachmentCategoryList.length) {
                    $scope.showAddMoreButton = false;
                }

                if (attachment.attachmentCategory.id) {
                    $scope.currentSelectItem[$index] = attachment.attachmentCategory.id;
                }
            } catch (e) {
                $scope.showAddMoreButton = false;
                $scope.mpoFormHasError = true;
                $scope.currentSelectItem.splice($index, 1);
            }

            if ($scope.attachmentCategoryList.length == arrayUnique($scope.currentSelectItem).length)
                $scope.mpoFormHasError = false;
            else
                $scope.mpoFormHasError = true;

            if (arrayUnique($scope.currentSelectItem).length != $scope.currentSelectItem.length)
                $scope.duplicateError = true;
            else
                $scope.duplicateError = false;
        }*/

       /* $scope.selectNoAttachment = function () {
            $scope.showAddMoreButton = true;

            if ($scope.applicantAttachmentCount.length == $scope.attachmentCategoryList.length) {
                $scope.showAddMoreButton = false;
            }
        }*/
        $scope.selectNoAttachment = function(val,val2,file,remarks)
        {
            console.log(val);
            console.log(val2);
            console.log(file);
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
                    if (val && (remarks == undefined || remarks=="")) {
                        $scope.mpoFormHasError = true;
                        $scope.showAddMoreButton = false;
                    }else{
                        $scope.showAddMoreButton = true;
                    }

                }
            }else{
                if(file==undefined){
                    $scope.mpoFormHasError = true;
                    $scope.showAddMoreButton = false;
                }else{
                    $scope.showAddMoreButton = true;
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

        function inArray(needle, haystack) {
            var length = haystack.length;
            for (var i = 0; i < length; i++) {
                if (typeof haystack[i] == 'object') {
                    if (arrayCompare(haystack[i], needle)) return true;
                } else {
                    if (haystack[i] == needle) return true;
                }
            }
            return false;
        }

        function arrayUnique(a) {
            return a.reduce(function (p, c) {
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
            console.log('comes to setfile');
            $scope.showAddMoreButton = true;
            $scope.mpoFormHasError = true;
            try {
                if ($file) {
                    var fileReader = new FileReader();
                    fileReader.readAsDataURL($file);
                    fileReader.onload = function (e) {
                        var base64Data = e.target.result.substr(e.target.result.indexOf('base64,') + 'base64,'.length);
                        $scope.$apply(function () {
                            try {
                                attachment.file = base64Data;
                                attachment.fileContentType = $file.type;
                                attachment.fileName = $file.name;
                            } catch (e) {
                                $scope.showAddMoreButton = false;
                            }
                        });
                    };

                    if ($scope.applicantAttachmentCount.length == $scope.attachmentCategoryList.length) {
                        $scope.showAddMoreButton = false;
                    }

                    if ($scope.attachmentCategoryList.length == arrayUnique($scope.currentSelectItem).length)
                        $scope.mpoFormHasError = false;
                    else
                        $scope.mpoFormHasError = true;
                }
            } catch (e) {
                $scope.showAddMoreButton = false;
                $scope.mpoFormHasError = true;
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
        $scope.changeActionFile = function ($file) {

            console.log('comes to change file');

            if ($file) {
                console.log($file);
                var fileReader = new FileReader();
                fileReader.readAsDataURL($file);
                fileReader.onload = function (e) {
                    var base64Data = e.target.result.substr(e.target.result.indexOf('base64,') + 'base64,'.length);
                    $scope.$apply(function () {
                        $scope.timeScaleApplication.disActionFile = base64Data;
                        console.log($scope.timeScaleApplication.disActionFile);
                        //attachment.fileContentType = $file.type;
                        $scope.timeScaleApplication.disActionFileName = $file.name;

                    });
                };


            }

        };

        $scope.disciplinaryActionClick = function(data){
            console.log(data);
            if(!data){
                $scope.noDisciplinaryAction = true;
                $scope.timeScaleApplication.disActionCaseNo=null;
                $scope.timeScaleApplication.disActionFileName=null;
                $scope.timeScaleApplication.disActionFile=null;
                $scope.timeScaleApplication.resulationDate=null;
                $scope.timeScaleApplication.agendaNumber=null;
            }else{
                $scope.noDisciplinaryAction = false;
            }

        }
        $scope.workingBreakClick = function(data){
            console.log(data);
            if(!data){
                $scope.noWorkingBreakClick = true;
                $scope.timeScaleApplication.workingBreakStart=null;
                $scope.timeScaleApplication.workingBreakEnd=null;
            }else{
                $scope.noWorkingBreakClick = false;
            }

        }


    }]);

