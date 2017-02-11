'use strict';

angular.module('stepApp').controller('AlmEmpLeaveApplicationDialogController',
    ['$scope', '$rootScope','$sce', '$http', '$stateParams', '$state', 'entity', 'AlmEmpLeaveApplication', 'AlmLeavGrpTypeMap', 'AlmLeaveRule', 'User', 'Principal', 'DateUtils', 'HrEmployeeInfo', 'AlmLeaveType', 'AlmEmpLeaveBalance','DataUtils',
        function($scope,  $rootScope, $sce, $http, $stateParams, $state, entity, AlmEmpLeaveApplication,AlmLeavGrpTypeMap, AlmLeaveRule, User, Principal, DateUtils, HrEmployeeInfo, AlmLeaveType, AlmEmpLeaveBalance,DataUtils) {

            $scope.almEmpLeaveApplication = entity;
            $scope.hremployeeinfos = [];
            $scope.almleavetypes = [];

            HrEmployeeInfo.query(function(result){
                angular.forEach(result,function(dtoInfo){
                    if(dtoInfo.activeStatus){
                        $scope.hremployeeinfos.push(dtoInfo);
                    }
                });
            });

            AlmLeaveType.query(function(result){
                angular.forEach(result,function(dtoInfo){
                    if(dtoInfo.activeStatus){
                        $scope.almleavetypes.push(dtoInfo);
                    }
                });
            });

            $scope.almLeaveRule = null;

            $scope.load = function(id) {
                AlmEmpLeaveApplication.get({id : id}, function(result) {
                    $scope.almEmpLeaveApplication = result;
                });
               // $scope.onChangeLeaveType();
            };

            HrEmployeeInfo.get({id: 'my'}, function (result) {

                $scope.hrEmployeeInfo = result;
                $scope.designationName      = $scope.hrEmployeeInfo.designationInfo.designationName;
                $scope.departmentName       = $scope.hrEmployeeInfo.departmentInfo.departmentName;
                $scope.workAreaName         = $scope.hrEmployeeInfo.workArea.typeName;
                //$scope.loadGroupByEmployee();
            });

            /*$scope.almLeaveGroup = null;
            $scope.loadGroupByEmployee = function(){
                $scope.almLeaveGroup = null;
                AlmEmpLeaveGroupMap.query(function(result){
                    angular.forEach(result,function(dtoInfo){
                        if(dtoInfo.employeeInfo.id == $scope.hrEmployeeInfo.id){
                            $scope.almLeaveGroup = dtoInfo.almLeaveGroup;
                            $scope.almEmpLeaveApplication.almLeaveGroup = dtoInfo.almLeaveGroup;
                        }
                    });
                    $scope.loadLeaveTypeByLeaveGroup();
                });
            };

            $scope.loadLeaveTypeByLeaveGroup = function(){
                $scope.almleavetypes = [];
                if($scope.almLeaveGroup){
                    AlmLeavGrpTypeMap.query(function(result){
                        angular.forEach(result,function(dtoInfo){
                            if(dtoInfo.almLeaveGroup.id == $scope.almLeaveGroup.id){
                                $scope.almleavetypes.push(dtoInfo.almLeaveType);
                            }
                        });
                    });
                }
            };*/

            $scope.onChangeLeaveType = function () {
                $scope.loadAlmLeaveRule();
                $scope.getEmpLeaveBalanceInfo();
            };
            $scope.isGenderCheck = true;
            $scope.genderErrorMessage = '';
            $scope.loadAlmLeaveRule = function(){
                $scope.isGenderCheck = true;
                $scope.almLeaveRule = null;
                AlmLeaveRule.query(function(result){
                    angular.forEach(result,function(dtoInfo){
                        if(dtoInfo.almLeaveType.id == $scope.almEmpLeaveApplication.almLeaveType.id){
                            console.log($scope.hrEmployeeInfo.gender);
                            if(dtoInfo.almGender != "Both" && dtoInfo.almGender != $scope.hrEmployeeInfo.gender){
                                $scope.isGenderCheck = false;
                                $scope.genderErrorMessage = "You are not allowed to take this leave."
                            }
                            $scope.almLeaveRule = dtoInfo;
                        }
                    });
                });
            };


            $scope.getEmpLeaveBalanceInfo = function(){
                $scope.almEmpLeaveApplication.leaveBalance = 0;
                AlmEmpLeaveBalance.query(function(result){
                    angular.forEach(result,function(dtoInfo) {
                        if(dtoInfo.employeeInfo.id == $scope.hrEmployeeInfo.id
                                && dtoInfo.almLeaveType.id == $scope.almEmpLeaveApplication.almLeaveType.id){
                            $scope.almEmpLeaveApplication.leaveBalance = dtoInfo.leaveBalance;
                            $scope.originalLeaveBalance = dtoInfo.leaveBalance;
                            $scope.almEmpLeaveBalance = dtoInfo;
                        }

                    });
                });

                /*var data = {
                    employeeId:     $scope.hrEmployeeInfo.id,
                    leaveTypeId:    $scope.almEmpLeaveApplication.almLeaveType.id

                };

                var config = {
                    params: data,
                    headers : {'Accept' : 'application/json'}
                };
                $http.get('api/almEmpLeaveBalByEmpInfoAndLeaveType/', config).then(function(response){

                    $scope.almEmpLeaveApplication.leaveBalance = response.data.leaveBalance;
                    $scope.leaveOnApply = response.data.leaveOnApply;


                }, function(response){
                    console.log("Failed: ");
                });*/
            };

            $scope.isDateCheck = true;
            $scope.fromDateCheckMsg = '';
            $scope.fromDateChecking = function() {
                $scope.isDateCheck = true;
                $scope.fromDateCheckMsg = '';
                if($scope.isHalfDayLeave){
                    $scope.almEmpLeaveApplication.leaveDays = 0.5;
                    $scope.almEmpLeaveApplication.leaveToDate = null;
                    $scope.almLeaveRuleCheck();
                }else{
                    $scope.fromDateF = $scope.almEmpLeaveApplication.leaveFromDate;
                    $scope.toDateT = $scope.almEmpLeaveApplication.leaveToDate;
                    if ($scope.fromDateF == '' || $scope.fromDateF == null) {
                        $scope.fromDateCheckMsg =  "Select From Date First";
                        $scope.isDateCheck = false;
                    }
                    if ($scope.fromDateF != '' && $scope.fromDateF != null && $scope.toDateT != '' && $scope.toDateT != null) {
                        if ($scope.fromDateF > $scope.toDateT) {
                            $scope.fromDateCheckMsg = "From Date should be less than End Date.";
                            $scope.isDateCheck = false;
                        }
                        $scope.t2 = $scope.toDateT.getTime();
                        $scope.t1 = $scope.fromDateF.getTime();
                        $scope.result = parseInt(($scope.t2 - $scope.t1) / (24 * 3600 * 1000));
                        $scope.almEmpLeaveApplication.leaveDays = $scope.result +1;
                        if( $scope.almEmpLeaveApplication.leaveDays > 15.0){
                            $scope.showDdo = true;
                        }else{
                            $scope.showDdo = false;
                        }
                        $scope.almLeaveRuleCheck();
                    }
                }

            };
            $scope.showDdo = false;
            $scope.toDateChecking = function() {
                $scope.isDateCheck = true;
                $scope.fromDateCheckMsg = '';
                //$scope.checkAlmLeaveRuleMinimumGap();
                $scope.duplicateCheckByFromDateAndToDate();
                $scope.fromDateF = $scope.almEmpLeaveApplication.leaveFromDate;
                $scope.toDateT = $scope.almEmpLeaveApplication.leaveToDate;
                if ($scope.fromDateF == '' || $scope.fromDateF == null) {
                    $scope.fromDateCheckMsg =  "Select From Date First";
                    $scope.isDateCheck = false;
                }
                if ($scope.fromDateF != '' && $scope.fromDateF != null && $scope.toDateT != '' && $scope.toDateT != null) {
                    if ($scope.toDateT  < $scope.fromDateF) {
                        $scope.fromDateCheckMsg = "From Date should be less than End Date.";
                        $scope.isDateCheck = false;
                    }
                    $scope.t2 = $scope.toDateT.getTime();
                    $scope.t1 = $scope.fromDateF.getTime();
                    $scope.result = parseInt(($scope.t2 - $scope.t1) / (24 * 3600 * 1000));
                    $scope.almEmpLeaveApplication.leaveDays = $scope.result +1;
                    if( $scope.almEmpLeaveApplication.leaveDays > 15.0){
                        $scope.showDdo = true;
                    }else{
                        $scope.showDdo = false;
                    }
                    $scope.almLeaveRuleCheck();
                }
            };

            $scope.isHalfDayLeaveCheck = function(){
                if($scope.almEmpLeaveApplication.isHalfDayLeave){

                    $scope.almEmpLeaveApplication.leaveDays = 0.5;
                    $scope.almEmpLeaveApplication.leaveToDate = null;
                    $scope.almLeaveRuleCheck();
                }
            };

            $scope.almLeaveRuleCheck = function(){
                $scope.checkAlmLeaveRuleNegativeBalance();
                $scope.checkAlmLeaveRuleConsecutiveBalance();
                $scope.checkAlmLeaveRuleNoOfInstance();
            };

            $scope.almNegErrorMessage = '';
            $scope.isNegativeBalanceCheck = true;
            $scope.checkAlmLeaveRuleNegativeBalance = function(){
                $scope.almEmpLeaveApplication.leaveBalance = 0;
                $scope.isNegativeBalanceCheck = true;
                $scope.almNegErrorMessage = '';
                var leaveBalance = 0;
                var originalLeaveBalance = 0;
                originalLeaveBalance = $scope.originalLeaveBalance;
                $scope.almEmpLeaveApplication.leaveBalance = originalLeaveBalance - $scope.almEmpLeaveApplication.leaveDays;
                leaveBalance = $scope.almEmpLeaveApplication.leaveBalance;
                if(!$scope.almLeaveRule.isNegBalanceAllowed && leaveBalance < 0){
                    $scope.almNegErrorMessage = 'Leave Balance Cannot be negative.';
                    $scope.isNegativeBalanceCheck = false;
                }else if($scope.almLeaveRule.isNegBalanceAllowed && -(leaveBalance) > $scope.almLeaveRule.maxNegBalance){
                    $scope.almNegErrorMessage = 'Leave Balance Cannot smaller than ' + $scope.almLeaveRule.maxNegBalance;
                    $scope.isNegativeBalanceCheck = false;
                }else{
                    $scope.isNegativeBalanceCheck = true;
                }
            };

            $scope.almConsectiveLeaveErrorMessage = '';
            $scope.isConsecutiveBalanceCheck = true;
            $scope.checkAlmLeaveRuleConsecutiveBalance = function(){
                $scope.isConsecutiveBalanceCheck = true;
                $scope.almConsectiveLeaveErrorMessage = '';

                if($scope.almEmpLeaveApplication.leaveDays > $scope.almLeaveRule.maxConsecutiveLeaves){
                    $scope.almConsectiveLeaveErrorMessage = 'Leave Days Cannot be greater than ' + $scope.almLeaveRule.maxConsecutiveLeaves;
                    $scope.isConsecutiveBalanceCheck = false;
                }
            };

            $scope.almMinGapErrorMessage = '';
            $scope.isMinimumGapCheck = true;
            $scope.checkAlmLeaveRuleMinimumGap = function(){
                $scope.isMinimumGapCheck = true;
                $scope.almMinGapErrorMessage = '';
                $scope.fromDateF = $scope.almEmpLeaveApplication.leaveFromDate;
                var gap = - $scope.almLeaveRule.minGapBetweenTwoLeaves;
                $scope.fd = new Date($scope.fromDateF.getTime() + gap*24*60*60*1000);
                console.log('Checking date: '+$scope.fd);
                AlmEmpLeaveApplication.query(function(result){
                    angular.forEach(result,function(dtoInfo){
                        if(dtoInfo.employeeInfo.id == $scope.hrEmployeeInfo.id
                            && dtoInfo.almLeaveType.id == $scope.almEmpLeaveApplication.almLeaveType.id
                            && dtoInfo.applicationLeaveStatus != 'Rejected'
                            && DateUtils.convertLocaleDateToServer($scope.fd) <= dtoInfo.leaveFromDate){
                            $scope.almMinGapErrorMessage = 'Minimum Gap Between two leaves ' + $scope.almLeaveRule.minGapBetweenTwoLeaves;
                            $scope.isMinimumGapCheck = false;
                        }
                    });
                });
            };

            $scope.isExitsData = true;
            $scope.duplicateCheckByFromDateAndToDate = function(){
                $scope.isExitsData = true;
                $scope.almDuplicateErrorMessage = '';
                $scope.fromDateL = $scope.almEmpLeaveApplication.leaveFromDate;
                AlmEmpLeaveApplication.query(function(result){
                    angular.forEach(result,function(dtoInfo){
                        if(dtoInfo.employeeInfo.id == $scope.hrEmployeeInfo.id
                            && dtoInfo.leaveFromDate == DateUtils.convertLocaleDateToServer($scope.fromDateL)){
                            $scope.isExitsData = false;
                            $scope.almDuplicateErrorMessage = 'You already applied leave for this day.';
                        }
                    });
                });
            };

            $scope.almLeaveNoOfInsErrorMessage = '';
            $scope.isNoOfInstanceCheck = true;
            $scope.checkAlmLeaveRuleNoOfInstance = function(){
                $scope.isNoOfInstanceCheck = true;
                $scope.almLeaveNoOfInsErrorMessage = '';
                var noOfInstance = 0;
                AlmEmpLeaveApplication.query(function(result){
                    angular.forEach(result,function(dtoInfo){
                        if(dtoInfo.employeeInfo.id == $scope.hrEmployeeInfo.id
                            && dtoInfo.almLeaveType.id == $scope.almEmpLeaveApplication.almLeaveType.id
                            && dtoInfo.applicationLeaveStatus == 'Approved'){
                            noOfInstance += 1;
                        }
                    });
                });
                if(noOfInstance > $scope.almLeaveRule.noOfInstanceAllowed){
                    $scope.almLeaveNoOfInsErrorMessage = 'You can not take this leave more than ' + $scope.almLeaveRule.noOfInstanceAllowed;
                    $scope.isNoOfInstanceCheck = false;
                }
            };

            $scope.loggedInUser =   {};
            $scope.getLoggedInUser = function ()
            {
                Principal.identity().then(function (account)
                {
                    User.get({login: account.login}, function (result)
                    {
                        $scope.loggedInUser = result;
                    });
                });
            };

            $scope.populateEmployeeInfo = function(employeeInfo)
            {

                HrEmployeeInfo.get({id: 'my'}, function (result) {
                    $scope.hrEmployeeInfo = result;

                });
                $scope.designationName      = $scope.hrEmployeeInfo.designationInfo.designationName;
                $scope.departmentName       = $scope.hrEmployeeInfo.departmentInfo.departmentName;
                $scope.workAreaName         = $scope.hrEmployeeInfo.workArea.typeName;
            };

            $scope.getLoggedInUser();

            $scope.abbreviate = DataUtils.abbreviate;

            $scope.byteSize = DataUtils.byteSize;

            $scope.setLeaveCertificate = function ($file, almEmpLeaveApplication) {
                if ($file) {
                    var fileReader = new FileReader();
                    fileReader.readAsDataURL($file);
                    fileReader.onload = function (e) {
                        var base64Data = e.target.result.substr(e.target.result.indexOf('base64,') + 'base64,'.length);
                        $scope.$apply(function() {
                            almEmpLeaveApplication.leaveCertificate = base64Data;
                            almEmpLeaveApplication.leaveCertContentType = $file.type;
                            if (almEmpLeaveApplication.id == null)
                            {
                                almEmpLeaveApplication.leaveCertificateName = $file.name;
                            }
                        });
                    };
                }
            };

            $scope.previewCertDoc = function (modelInfo)
            {
                var blob = $rootScope.b64toBlob(modelInfo.leaveCertificate, modelInfo.leaveCertificateContentType);
                $rootScope.viewerObject.contentUrl = $sce.trustAsResourceUrl((window.URL || window.webkitURL).createObjectURL(blob));
                $rootScope.viewerObject.contentType = modelInfo.leaveCertificateContentType;
                $rootScope.viewerObject.pageTitle = "Certificate Document";
                $rootScope.showPreviewModal();
            };

            var onSaveFinished = function (result) {
                $scope.$emit('stepApp:almEmpLeaveApplicationUpdate', result);
                $scope.isSaving = false;

                if ($scope.almEmpLeaveBalance) {
                    $scope.almEmpLeaveBalance.leaveBalance = $scope.almEmpLeaveApplication.leaveBalance;
                    $scope.almEmpLeaveBalance.updateBy = $scope.loggedInUser.id;
                    $scope.almEmpLeaveBalance.updateDate = DateUtils.convertLocaleDateToServer(new Date());
                    $scope.almEmpLeaveBalance.leaveOnApply = $scope.almEmpLeaveBalance.leaveOnApply + $scope.almEmpLeaveApplication.leaveDays;
                    AlmEmpLeaveBalance.update($scope.almEmpLeaveBalance);
                }
                $state.go("almEmpLeaveApplication");
            };

            $scope.save = function () {
                $scope.almEmpLeaveApplication.updateBy = $scope.loggedInUser.id;
                $scope.almEmpLeaveApplication.updateDate = DateUtils.convertLocaleDateToServer(new Date());
                $scope.almEmpLeaveApplication.applicationDate = DateUtils.convertLocaleDateToServer(new Date());
                $scope.almEmpLeaveApplication.employeeInfo = $scope.hrEmployeeInfo;
                $scope.almEmpLeaveApplication.applicationLeaveStatus = 'Pending';
                if ($scope.almEmpLeaveApplication.id != null) {
                    AlmEmpLeaveApplication.update($scope.almEmpLeaveApplication, onSaveFinished);
                    $rootScope.setWarningMessage('stepApp.almEmpLeaveApplication.updated');
                } else {
                    $scope.almEmpLeaveApplication.createBy = $scope.loggedInUser.id;
                    $scope.almEmpLeaveApplication.createDate = DateUtils.convertLocaleDateToServer(new Date());
                    AlmEmpLeaveApplication.save($scope.almEmpLeaveApplication, onSaveFinished);
                    $rootScope.setSuccessMessage('stepApp.almEmpLeaveApplication.created');
                }
            };
            $scope.calendar = {
                opened: {},
                dateFormat: 'dd-MM-yyyy',
                dateOptions: {},
                open: function ($event, which) {
                    $event.preventDefault();
                    $event.stopPropagation();
                    $scope.calendar.opened[which] = true;
                }
            };

            $scope.date = new Date();

            $scope.initiateAlmEmpLeaveBalanceModel = function()
            {
                return {
                    yearOpenDate: null,
                    year: null,
                    leaveEarned: null,
                    leaveTaken: null,
                    leaveForwarded: null,
                    attendanceLeave: null,
                    leaveOnApply: null,
                    leaveBalance: null,
                    activeStatus: true
                };
            };

        }]);


