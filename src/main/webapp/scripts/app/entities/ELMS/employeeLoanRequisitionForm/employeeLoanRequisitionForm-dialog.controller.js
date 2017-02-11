'use strict';

angular.module('stepApp').controller('EmployeeLoanRequisitionFormDialogController',
            ['$scope','$rootScope' ,'$state','$stateParams', 'entity', 'EmployeeLoanRequisitionForm', 'EmployeeLoanTypeSetup', 'EmployeeLoanRulesSetup','AttachmentCategoryByApplicationName','EmployeeLoanAttachment','Employee',
                'EmployeeLoanRulesList','ValidateLoanAmountAndBasicSalary','ValidateLoanInstallment','CurrentHrEmployeeInfo','LoanAttachmentByEmployeeAndAppName','Principal','FindEmployeeLoanTypeSetupByStatus','DateUtils',
            function($scope,$rootScope,$state, $stateParams,entity, EmployeeLoanRequisitionForm, EmployeeLoanTypeSetup, EmployeeLoanRulesSetup,AttachmentCategoryByApplicationName,EmployeeLoanAttachment,Employee,
                     EmployeeLoanRulesList,ValidateLoanAmountAndBasicSalary,ValidateLoanInstallment,CurrentHrEmployeeInfo,LoanAttachmentByEmployeeAndAppName,Principal,FindEmployeeLoanTypeSetupByStatus,DateUtils) {

        $scope.employeeLoanRequisitionForm = entity;
        $scope.employeeloantypesetups = FindEmployeeLoanTypeSetupByStatus.query();

        $scope.employeeLoanRulesSetups = [];
        $scope.pageShow = true;
        $scope.showAddMoreButton = false;
        $scope.applicantAttachment = [];
        $scope.applicantAttachmentCount = [0];
        $scope.attachmentCategoryList = [];
        $scope.currentSelectItem = [];
        $scope.loanReqFormHasError = true;
        $scope.employee = [];
        $scope.errorForInstallment = false;
        $scope.errorForLoanAmount = false;
        $scope.errorApplyForLoan = false;
        $scope.invalidLoanAmount = false;
        $scope.loanReqFormEditMode = false;
        $scope.attachments = [];

        CurrentHrEmployeeInfo.get({},function(hrEmployeeData){
            $scope.employee = hrEmployeeData;
            AttachmentCategoryByApplicationName.query({name:'Employee-Loan-Application'}, function (result){
               $scope.attachmentCategoryList = result;
            });
            LoanAttachmentByEmployeeAndAppName.query({id:hrEmployeeData.id, applicationName:"Employee-Loan-Application"}, function(result){
                $scope.attachments = result;
            });
        });

        EmployeeLoanRequisitionForm.get({id : entity.id}, function(result) {
            $scope.employeeLoanRulesSetups.push(result.employeeLoanRulesSetup);
            console.log('Emp loan name'+result.employeeLoanRulesSetup.loanName);
        });

        var onSaveFinished = function (result) {

            angular.forEach($scope.applicantAttachmentCount, function (value, key) {
                if ($scope.applicantAttachment[value].attachment != '') {
                    $scope.applicantAttachment[value].hrEmployeeInfo = {};
                    $scope.applicantAttachment[value].name = $scope.applicantAttachment[value].attachmentCategory.attachmentName;
                    $scope.applicantAttachment[value].hrEmployeeInfo = $scope.employee;
                    $scope.applicantAttachment[value].employeeLoanRequisitionForm = result;
                    EmployeeLoanAttachment.save($scope.applicantAttachment[value]);
                }
            });
            $rootScope.setSuccessMessage('Save successfully');
            $state.go('employeeLoanInfo.employeeLoanRequisitionForm',{},{reload:true});
            $scope.$emit('stepApp:employeeLoanRequisitionFormUpdate', result);
        };

        $scope.save = function () {
            if ($scope.employeeLoanRequisitionForm.id != null) {
                $scope.employeeLoanRequisitionForm.updateDate = DateUtils.convertLocaleDateToServer(new Date());
                EmployeeLoanRequisitionForm.update($scope.employeeLoanRequisitionForm, onSaveFinished);

                angular.forEach($scope.attachments, function (value, key) {
                    if ($scope.attachments[key].attachment != '') {
                      EmployeeLoanAttachment.update(value);
                    }
                });

            } else {
                $scope.employeeLoanRequisitionForm.createDate = DateUtils.convertLocaleDateToServer(new Date());
                EmployeeLoanRequisitionForm.save($scope.employeeLoanRequisitionForm,onSaveFinished);
                //$state.go('employeeLoanInfo.employeeLoanRequisitionForm',{},{reload:true});
            }
        };

        $scope.clear = function() {
             $state.go('employeeLoanInfo.employeeLoanRequisitionForm',{},{reload:true});
        };

        $scope.isPageShow = function(value) {
              $scope.pageShow = value;
        };

         $scope.isPageBack = function() {
             $scope.pageShow = true;
         };

        $scope.setAttachment = function($index, attachment,noAttach) {
            if(noAttach ){
                if($scope.applicantAttachmentCount.length == $scope.attachmentCategoryList.length){
                    $scope.showAddMoreButton = false;
                }else{
                    $scope.showAddMoreButton = true;
                }
                if($scope.loanReqFormEditMode) $scope.loanReqFormHasError = false;
            }
            try{
                if(attachment==""){
                    $scope.loanReqFormHasError = true;
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
                if($scope.loanReqFormEditMode) $scope.loanReqFormHasError = false;

            }catch(e) {
                $scope.showAddMoreButton = false;
                $scope.loanReqFormHasError = true;
                $scope.currentSelectItem.splice($index, 1);
            }
            if($scope.attachmentCategoryList.length == arrayUnique($scope.currentSelectItem).length){
                $scope.loanReqFormHasError = false;
                angular.forEach($scope.applicantAttachment, function (value, key) {
                    console.log(value);

                    if (value.noAttachment && (value.remarks == undefined || value.remarks=="")) {
                        $scope.loanReqFormHasError = true;
                    }else {
                        if (value.fileName) {
                            $scope.loanReqFormHasError = true;
                        }
                    }

                });
            }
            else{
                angular.forEach($scope.applicantAttachment, function (value, key) {
                    console.log(value);

                    if (!value.noAttachment && (value.fileName)) {
                        $scope.loanReqFormHasError = true;

                    }
                });
            }

            if($scope.loanReqFormEditMode) $scope.loanReqFormHasError = false;

            if(arrayUnique($scope.currentSelectItem).length != $scope.currentSelectItem.length)
                $scope.duplicateError = true;
            else
                $scope.duplicateError = false;

            if($scope.duplicateError){
                $scope.showAddMoreButton = false;
            }
        }

        $scope.setFile = function ($file, attachment) {
            $scope.showAddMoreButton = true;
            $scope.loanReqFormHasError = true;
            try{
                if ($file) {
                    var fileReader = new FileReader();
                    fileReader.readAsDataURL($file);
                    fileReader.onload = function (e) {

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


                    if($scope.applicantAttachmentCount.length == $scope.attachmentCategoryList.length){
                        $scope.showAddMoreButton = false;
                    }

                    if($scope.attachmentCategoryList.length == arrayUnique($scope.currentSelectItem).length)
                        $scope.loanReqFormHasError = false;
                    else
                        $scope.loanReqFormHasError = true;

                    if($scope.loanReqFormEditMode) $scope.loanReqFormHasError = false;
                }
            }catch(e) {
                $scope.showAddMoreButton = false;
                $scope.loanReqFormHasError = true;
                if($scope.loanReqFormEditMode) $scope.loanReqFormHasError = false;
            }
        };

        $scope.setFileForEdit = function ($file, attachment) {
                try{
                    if ($file) {
                        console.log(attachment);
                        var fileReader = new FileReader();
                        fileReader.readAsDataURL($file);
                        fileReader.onload = function (e) {

                            var base64Data = e.target.result.substr(e.target.result.indexOf('base64,') + 'base64,'.length);
                            $scope.$apply(function() {
                                try{
                                    attachment.file = base64Data;
                                    attachment.fileContentType = $file.type;
                                    attachment.fileName = $file.name;
                                }catch(e) {
                                    console.log('Inside Catch Set File Method');
                                }
                            });
                        };
                    }
                }catch(e) {

                    if($scope.loanReqFormEditMode) $scope.loanReqFormHasError = false;
                }
        };


        $scope.addMoreAttachment = function () {
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
                    $scope.loanReqFormHasError = true;
                    var index =  $scope.applicantAttachmentCount.indexOf(attachment);
                    $scope.applicantAttachmentCount.splice(index,1);

                    if($scope.applicantAttachmentCount.length == $scope.attachmentCategoryList.length-1) {
                        $scope.showAddMoreButton = false;
                        $scope.loanReqFormHasError = false;
                    }
        }

        function arrayUnique(a) {
                    return a.reduce(function(p, c) {
                        if (p.indexOf(c) < 0) p.push(c);
                        return p;
                    }, []);
        };

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

         $scope.selectNoAttachment = function(val,val2,file){

                if(val && val2){
                    if($scope.applicantAttachmentCount.length === $scope.attachmentCategoryList.length){

                        $scope.showAddMoreButton = false;
                        $scope.loanReqFormHasError = false;
                        angular.forEach($scope.applicantAttachment, function (value, key) {
                                if (value.noAttachment && (value.remarks == undefined || value.remarks=="")) {
                                    $scope.loanReqFormHasError = true;
                                }
                        });

                    }else{
                        $scope.showAddMoreButton = true;
                    }
                }else{
                    if(file==undefined){
                        $scope.loanReqFormHasError = true;
                    }

                    if($scope.applicantAttachmentCount.length == $scope.attachmentCategoryList.length){
                        $scope.showAddMoreButton = false;
                    }
                }
                if($scope.applicantAttachmentCount.length === $scope.attachmentCategoryList.length){
                    $scope.showAddMoreButton = false;
                }
         }

         $scope.remarksChange = function(val,val2)
         {

             if($scope.applicantAttachmentCount.length === $scope.attachmentCategoryList.length){
                 $scope.showAddMoreButton = false;
                 $scope.loanReqFormHasError = false;
                 angular.forEach($scope.applicantAttachment, function (value, key) {

                     if (value.noAttachment && (value.remarks == undefined || value.remarks=="")) {
                         $scope.loanReqFormHasError = true;
                     }
                 });
             }
             if($scope.loanReqFormEditMode){
                $scope.loanReqFormHasError = false;
             }
         }

        $scope.previewImage = function (content, contentType,name) {
                        var blob = $rootScope.b64toBlob(content, contentType);
                        $rootScope.viewerObject.content = (window.URL || window.webkitURL).createObjectURL(blob);
                        $rootScope.viewerObject.contentType = contentType;
                        $rootScope.viewerObject.pageTitle = name;
                        // call the modal
                        $rootScope.showFilePreviewModal();
        };

        $scope.editAttachment = function(){
                $scope.browseButton = false;
        }

        $scope.getEmployeeLoanRulesByLoanType = function () {
            if ($scope.employeeLoanRequisitionForm.employeeLoanTypeSetup != null) {
                EmployeeLoanRulesList.query({loanTypeID: $scope.employeeLoanRequisitionForm.employeeLoanTypeSetup.id}, function (result) {
                $scope.employeeLoanRulesSetups = result;
                });
            }
        };

        $scope.checkAmountForLoan = function(){
             $scope.errorForLoanAmount = false;
             $scope.errorForBasicSalary = false;
             $scope.amountInvalid = false;
             $scope.invalidLoanAmount = false;
            if($scope.employeeLoanRequisitionForm.employeeLoanRulesSetup != null && $scope.employeeLoanRequisitionForm.amount !=null){
                ValidateLoanAmountAndBasicSalary.get({loanRuleId:$scope.employeeLoanRequisitionForm.employeeLoanRulesSetup.id,
                                                        amount:$scope.employeeLoanRequisitionForm.amount,
                                                        employeeInfoId:$scope.employee.id},function(result){
                    var data = result[0];
                    // console.log(result);
                    if(data == 0){
                        alert('status '+ 0);
                    }
                     if(data == 2){
                        console.log('status 2');
                        $scope.errorForBasicSalary = true;
                         $scope.amountInvalid = true;
                     }
                     if(data == 3){
                         console.log('status 3');
                         $scope.errorForLoanAmount = true;
                         $scope.amountInvalid = true;
                     }
                     if(data == 4){
                         console.log('status 4');
                         $scope.errorApplyForLoan = true;
                         $scope.amountInvalid = true;
                     }
                });
            }else{
                $scope.invalidLoanAmount = true;
            }
        }


        $scope.checkLoanInstallment = function(){
            $scope.errorForInstallment = false;
            $scope.installmentInvalid = false;
            if($scope.employeeLoanRequisitionForm.installment != null){
                ValidateLoanInstallment.get({loanRulesId:$scope.employeeLoanRequisitionForm.employeeLoanRulesSetup.id},function(result){
                     if(result.installment < $scope.employeeLoanRequisitionForm.installment){
                        $scope.errorForInstallment = true;
                        $scope.installmentInvalid = true;

                     }
                });
            }
        }
}]);
