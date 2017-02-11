'use strict';

angular.module('stepApp').controller('AlmEmpLeaveInitializeDialogController',
    ['$scope', '$rootScope','$stateParams', '$state', 'entity', 'AlmEmpLeaveInitialize','AlmLeaveRule','User','Principal','DateUtils','AlmLeaveType','AlmEmpLeaveBalance','MiscTypeSetupByCategory','HrEmployeeInfoByWorkArea',
        function($scope, $rootScope, $stateParams, $state, entity, AlmEmpLeaveInitialize, AlmLeaveRule, User, Principal, DateUtils,AlmLeaveType,AlmEmpLeaveBalance,MiscTypeSetupByCategory,HrEmployeeInfoByWorkArea) {

            $scope.almEmpLeaveInitialize = entity;
            $scope.hremployeeinfos = [];
            $scope.almleavetypes = [];

            $scope.workAreaList     = MiscTypeSetupByCategory.get({cat:'EmployeeWorkArea',stat:'true'});
            $scope.loadModelByWorkArea = function(workArea)
            {
                HrEmployeeInfoByWorkArea.get({areaid : workArea.id}, function(result) {
                    $scope.hremployeeinfos = result;
                    console.log("Total record: "+result.length);
                });
            };

           /* HrEmployeeInfo.query(function(result){
                angular.forEach(result,function(dtoInfo){
                    if(dtoInfo.activeStatus){
                        $scope.hremployeeinfos.push(dtoInfo);
                    }
                });
            });*/

            $scope.populateEmployeeInfo = function()
            {
                if($scope.almEmpLeaveTypeMap.employeeInfo){
                    $scope.designationName      = $scope.almEmpLeaveTypeMap.employeeInfo.designationInfo.designationName;
                    $scope.departmentName       = $scope.almEmpLeaveTypeMap.employeeInfo.departmentInfo.departmentName;
                }

            };

            AlmLeaveType.query(function(result){
                angular.forEach(result,function(dtoInfo){
                    if(dtoInfo.activeStatus){
                        $scope.almleavetypes.push(dtoInfo);
                    }
                });
            });

            $scope.getEmpLeaveBalanceInfo = function(){
                $scope.loadAlmLeaveRule();
                $scope.almEmpLeaveBalance = null;
                $scope.curLeaveEarned = 0;
                $scope.curLeaveTaken = 0;
                $scope.curLeaveForwarded = 0;
                $scope.curLeaveOnApply = 0;
                $scope.curLeaveBalance = 0;
                console.log("getEmpLeaveBalanceInfo checking..");

                AlmEmpLeaveBalance.query(function(result){
                    angular.forEach(result,function(dtoInfo) {
                        if(dtoInfo.employeeInfo.id == $scope.almEmpLeaveInitialize.employeeInfo.id
                            && dtoInfo.almLeaveType.id == $scope.almEmpLeaveInitialize.almLeaveType.id)
                            {
                            console.log("AlmEmpLeaveBalance checking..");
                            $scope.curLeaveEarned     = dtoInfo.leaveEarned;
                            $scope.curLeaveTaken      = dtoInfo.leaveTaken;
                            $scope.curLeaveForwarded  = dtoInfo.leaveForwarded;
                            $scope.curLeaveOnApply    = dtoInfo.leaveOnApply;
                            $scope.curLeaveBalance    = dtoInfo.leaveBalance;
                            $scope.almEmpLeaveBalance = dtoInfo;
                        }

                    });
                });
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
            $scope.getLoggedInUser();

            $scope.isGenderCheck = true;
            $scope.genderErrorMessage = '';
            $scope.loadAlmLeaveRule = function(){
                $scope.isGenderCheck = true;
                $scope.almLeaveRule = null;
                AlmLeaveRule.query(function(result){
                    angular.forEach(result,function(dtoInfo){
                        if(dtoInfo.almLeaveType.id == $scope.almEmpLeaveInitialize.almLeaveType.id){
                            if(dtoInfo.almGender != "Both" && dtoInfo.almGender != $scope.almEmpLeaveInitialize.employeeInfo.gender){
                                $scope.isGenderCheck = false;
                                $scope.genderErrorMessage = "You are not allowed to take this leave."
                            }
                            $scope.almLeaveRule = dtoInfo;
                        }
                    });
                });
            };

            var onSaveFinished = function (result) {
                $scope.$emit('stepApp:almEmpLeaveInitializeUpdate', result);
                $scope.isSaving = false;
                console.log("Leave Initiazation - Completed");
                if ($scope.almEmpLeaveBalance)
                {
                    $scope.almEmpLeaveBalance.leaveBalance      = $scope.almEmpLeaveInitialize.leaveBalance;
                    $scope.almEmpLeaveBalance.leaveOnApply      = $scope.almEmpLeaveInitialize.leaveOnApply;
                    $scope.almEmpLeaveBalance.leaveTaken        = $scope.almEmpLeaveInitialize.leaveTaken;
                    $scope.almEmpLeaveBalance.leaveEarned       = $scope.almEmpLeaveInitialize.leaveEarned;
                    $scope.almEmpLeaveBalance.leaveForwarded    = $scope.almEmpLeaveInitialize.leaveForwarded;
                    $scope.almEmpLeaveBalance.updateBy          = $scope.loggedInUser.id;
                    $scope.almEmpLeaveBalance.updateDate        = DateUtils.convertLocaleDateToServer(new Date());
                    AlmEmpLeaveBalance.update($scope.almEmpLeaveBalance, onSaveLeaveBalanceFinished);
                }
                else
                {
                    $scope.almEmpLeaveBalance = $scope.initiateAlmEmpLeaveBalanceModel();

                    $scope.almEmpLeaveBalance.leaveBalance      = $scope.almEmpLeaveInitialize.leaveBalance;
                    $scope.almEmpLeaveBalance.leaveOnApply      = $scope.almEmpLeaveInitialize.leaveOnApply;
                    $scope.almEmpLeaveBalance.leaveTaken        = $scope.almEmpLeaveInitialize.leaveTaken;
                    $scope.almEmpLeaveBalance.leaveEarned       = $scope.almEmpLeaveInitialize.leaveEarned;
                    $scope.almEmpLeaveBalance.leaveForwarded    = $scope.almEmpLeaveInitialize.leaveForwarded;
                    $scope.almEmpLeaveBalance.updateBy          = $scope.loggedInUser.id;
                    $scope.almEmpLeaveBalance.updateDate        = DateUtils.convertLocaleDateToServer(new Date());
                    $scope.almEmpLeaveBalance.employeeInfo      = $scope.almEmpLeaveInitialize.employeeInfo;
                    $scope.almEmpLeaveBalance.almLeaveType      = $scope.almEmpLeaveInitialize.almLeaveType;
                    $scope.almEmpLeaveBalance.attendanceLeave   = 0;
                    $scope.almEmpLeaveBalance.activeStatus      = true;
                    $scope.almEmpLeaveBalance.yearOpenDate      = DateUtils.convertLocaleDateToServer(new Date());
                    $scope.almEmpLeaveBalance.createBy          = $scope.loggedInUser.id;
                    $scope.almEmpLeaveBalance.createDate        = DateUtils.convertLocaleDateToServer(new Date());
                    AlmEmpLeaveBalance.save($scope.almEmpLeaveBalance, onSaveLeaveBalanceFinished);
                }
            };

            var onSaveLeaveBalanceFinished = function (result) {
                $scope.$emit('stepApp:almEmpLeaveBalanceUpdate', result);
                $scope.isSaving = false;
                console.log("Leave Balance - Completed");
                $state.go("almEmpLeaveInitialize");
            };

            $scope.save = function ()
            {
                $scope.almEmpLeaveInitialize.updateBy = $scope.loggedInUser.id;
                $scope.almEmpLeaveInitialize.updateDate = DateUtils.convertLocaleDateToServer(new Date());
                //console.log(JSON.stringify($scope.almEmpLeaveInitialize));
                if ($scope.almEmpLeaveInitialize.id != null)
                {
                    AlmEmpLeaveInitialize.update($scope.almEmpLeaveInitialize, onSaveFinished);
                    $rootScope.setWarningMessage('stepApp.almEmpLeaveInitialize.updated');
                }
                else
                {
                    $scope.almEmpLeaveInitialize.createBy = $scope.loggedInUser.id;
                    $scope.almEmpLeaveInitialize.effectiveDate = DateUtils.convertLocaleDateToServer(new Date());
                    $scope.almEmpLeaveInitialize.createDate = DateUtils.convertLocaleDateToServer(new Date());
                    AlmEmpLeaveInitialize.save($scope.almEmpLeaveInitialize, onSaveFinished);
                    $rootScope.setSuccessMessage('stepApp.almEmpLeaveInitialize.created');
                }
            };

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


