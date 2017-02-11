angular.module('stepApp').controller('NameCancelApplicationController',
    ['$scope', '$stateParams', '$parse', '$state', 'entity','ProfessorApplication','InstEmployeeOfCurrentInstByCode', 'Auth','TimeScaleApplicationCheck', 'NameCnclApplication','Principal','TimeScaleApplication', '$rootScope', 'CurrentInstEmployee', 'Employee', 'User', 'Institute', 'ApplicantEducation', 'Training', 'AttachmentCategoryModule', 'Attachment', 'MpoApplication', 'InstEmployeeCode', 'InstEmployeeAddressInstitute', 'AttachmentEmployee', 'MpoApplicationCheck','APScaleApplication','AttachmentCategoryByApplicationName','InstEmployeeOfCurrentInstByIndex','NameCnclApplicationListCurrInst','NameCnclApplicationByEmpCode',
    function ($scope, $stateParams, $parse, $state, entity,ProfessorApplication,InstEmployeeOfCurrentInstByCode, Auth,TimeScaleApplicationCheck, NameCnclApplication,Principal,TimeScaleApplication, $rootScope, CurrentInstEmployee, Employee, User, Institute, ApplicantEducation, Training, AttachmentCategoryModule, Attachment, MpoApplication, InstEmployeeCode, InstEmployeeAddressInstitute, AttachmentEmployee, MpoApplicationCheck,APScaleApplication,AttachmentCategoryByApplicationName, InstEmployeeOfCurrentInstByIndex, NameCnclApplicationListCurrInst, NameCnclApplicationByEmpCode) {

        $scope.currentSection = 'personal';
        $scope.applicantAttachment = [];
        $scope.employee = {};
        $scope.applicantAttachmentCount = [0];
        $scope.attachmentCategories = [];
        $scope.attachmentList = [];
        $scope.mpoAttachmentType = '';
        $scope.mpoApplication = {};
        $scope.search = null;
        $scope.mpoForm = false;
        $scope.showAddMoreButton = false;
        $scope.currentSelectItem = [];
        $scope.mpoFormHasError = true;
        $scope.duplicateError = false;
        $scope.showMsg = false;
        $scope.nameCnclApplication = {};
        $scope.noEmpFound = false;
        $scope.teacherIndexNo = null;
        $scope.teacherFoundMsg = false;

        $scope.searchTeacher = function (teacherIndexNo) {
            console.log(teacherIndexNo);
            InstEmployeeOfCurrentInstByIndex.get({index: teacherIndexNo}, function(result) {
                $scope.employee = result;
                /*console.log('result found');
                console.log(result);*/
                NameCnclApplicationByEmpCode.get({code: result.code}, function(application){
                    //console.log('already applied');
                    $state.go('instituteNameCancelList', {}, {reload: true});
                    $rootScope.setSuccessMessage('Application Already Submitted! ');

                });
                $scope.teacherFoundMsg = false;
            }, function(response) {
                if(response.status === 404) {
                    $scope.teacherFoundMsg = true;
                    $scope.employee = {};
                    console.log('result not found.......');
                }if(response.status === 500) {
                    $scope.teacherFoundMsg = true;
                    $scope.employee={};
                    console.log('result not found.......');
                }
            });
        };

        AttachmentCategoryByApplicationName.get({name: 'NameCancle'}, function (result) {
            $scope.attachmentCategoryList = result;
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
            //mpoApplication entry

            if($scope.employee){

            }
            $scope.isSaving = true;

            $scope.nameCnclApplication.instEmployee = $scope.employee;

            console.log($scope.applicantAttachmentCount);
            //employee attachment
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
            NameCnclApplication.save($scope.nameCnclApplication);
            $rootScope.setSuccessMessage('Application Submitted Successfully.');
            $scope.isSaving = false;
            $state.go('nameCncl-statusLog', {'code':$scope.employee.code}, {reload: true});
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
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



            if($scope.applicantAttachmentCount.length === $scope.attachmentCategoryList.length){
                $scope.showAddMoreButton = false;
            }
        };

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


    }]);

