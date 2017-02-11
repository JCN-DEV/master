'use strict';

angular.module('stepApp').controller('AlmEmpLeaveTypeMapDialogController',
    ['$scope', '$rootScope','$stateParams', '$state', 'entity', 'AlmEmpLeaveTypeMap', 'AlmLeaveRule','User', 'Principal', 'DateUtils', 'AlmEmpLeaveBalance', 'AlmLeaveType', 'HrEmployeeInfoByWorkArea', 'MiscTypeSetupByCategory',
        function($scope, $rootScope, $stateParams, $state, entity, AlmEmpLeaveTypeMap, AlmLeaveRule, User, Principal, DateUtils ,AlmEmpLeaveBalance, AlmLeaveType, HrEmployeeInfoByWorkArea, MiscTypeSetupByCategory) {

            $scope.almEmpLeaveTypeMap = entity;
            $scope.hremployeeinfos = [];
            $scope.almleavetypes = [];

            //HrEmployeeInfo.query(function(result){
            //    angular.forEach(result,function(dtoInfo){
            //        if(dtoInfo.activeStatus){
            //            $scope.hremployeeinfos.push(dtoInfo);
            //        }
            //    });
            //});

            $scope.workAreaList     = MiscTypeSetupByCategory.get({cat:'EmployeeWorkArea',stat:'true'});
            $scope.loadModelByWorkArea = function(workArea)
            {
                HrEmployeeInfoByWorkArea.get({areaid : workArea.id}, function(result) {
                    $scope.hremployeeinfos = result;
                    console.log("Total record: "+result.length);
                });
            };

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

            $scope.load = function(id) {
                AlmEmpLeaveTypeMap.get({id : id}, function(result) {
                    $scope.almEmpLeaveTypeMap = result;
                });
            };

            /*$scope.almLeaveGroup = null;
            $scope.loadGroupByEmployee = function(){
                $scope.almLeaveGroup = null;
                if(!$scope.almEmpLeaveTypeMap.employeeInfo){
                    return;
                }
                AlmEmpLeaveGroupMap.query(function(result){
                    angular.forEach(result,function(dtoInfo){
                        if(dtoInfo.employeeInfo.id == $scope.almEmpLeaveTypeMap.employeeInfo.id){
                            $scope.almLeaveGroup = dtoInfo.almLeaveGroup;
                            $scope.almEmpLeaveTypeMap.almLeaveGroup = dtoInfo.almLeaveGroup;
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

            $scope.getEmpLeaveBalanceInfoByType = function() {
                $scope.loadAlmLeaveRule();
                $scope.almEmpLeaveTypeMap.currentBalance = 0;
                AlmEmpLeaveBalance.query(function (result) {
                    angular.forEach(result, function (dtoInfo) {
                        if (dtoInfo.employeeInfo.id == $scope.almEmpLeaveTypeMap.employeeInfo.id
                            && dtoInfo.almLeaveType.id == $scope.almEmpLeaveTypeMap.almLeaveType.id) {
                            $scope.almEmpLeaveTypeMap.currentBalance = dtoInfo.leaveBalance;
                            $scope.almEmpLeaveBalance = dtoInfo;
                        }

                    });
                });
            };

            $scope.isGenderCheck = true;
            $scope.genderErrorMessage = '';
            $scope.loadAlmLeaveRule = function(){
                $scope.isGenderCheck = true;
                $scope.almLeaveRule = null;
                AlmLeaveRule.query(function(result){
                    angular.forEach(result,function(dtoInfo){
                        if(dtoInfo.almLeaveType.id == $scope.almEmpLeaveTypeMap.almLeaveType.id){
                            if(dtoInfo.almGender != "Both" && dtoInfo.almGender != $scope.almEmpLeaveTypeMap.employeeInfo.gender){
                                $scope.isGenderCheck = false;
                                $scope.genderErrorMessage = "You are not allowed to take this leave."
                            }
                            $scope.almLeaveRule = dtoInfo;
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

            var onSaveFinished = function (result) {
                $scope.$emit('stepApp:almEmpLeaveTypeMapUpdate', result);
                $scope.isSaving = false;

                if ($scope.almEmpLeaveBalance) {
                    $scope.almEmpLeaveBalance.leaveBalance = $scope.almEmpLeaveTypeMap.newBalance;
                    $scope.almEmpLeaveBalance.updateBy = $scope.loggedInUser.id;
                    $scope.almEmpLeaveBalance.updateDate = DateUtils.convertLocaleDateToServer(new Date());
                    AlmEmpLeaveBalance.update($scope.almEmpLeaveBalance);
                } else {

                    $scope.almEmpLeaveBalance = $scope.initiateAlmEmpLeaveBalanceModel();
                    $scope.almEmpLeaveBalance.leaveBalance = $scope.almEmpLeaveTypeMap.newBalance;
                    $scope.almEmpLeaveBalance.updateBy = $scope.loggedInUser.id;
                    $scope.almEmpLeaveBalance.updateDate = DateUtils.convertLocaleDateToServer(new Date());
                    $scope.almEmpLeaveBalance.leaveOnApply = 0;
                    $scope.almEmpLeaveBalance.leaveTaken = 0;
                    $scope.almEmpLeaveBalance.employeeInfo = $scope.almEmpLeaveTypeMap.employeeInfo;
                    $scope.almEmpLeaveBalance.almLeaveType = $scope.almEmpLeaveTypeMap.almLeaveType;
                    $scope.almEmpLeaveBalance.leaveEarned = 0;
                    $scope.almEmpLeaveBalance.leaveForwarded = 0;
                    $scope.almEmpLeaveBalance.attendanceLeave = 0;
                    $scope.almEmpLeaveBalance.activeStatus = true;
                    $scope.almEmpLeaveBalance.yearOpenDate = DateUtils.convertLocaleDateToServer(new Date());
                    $scope.almEmpLeaveBalance.createBy = $scope.loggedInUser.id;
                    $scope.almEmpLeaveBalance.createDate = DateUtils.convertLocaleDateToServer(new Date());
                    AlmEmpLeaveBalance.save($scope.almEmpLeaveBalance);
                }
                $state.go("almEmpLeaveTypeMap");
            };

            $scope.save = function () {
                $scope.almEmpLeaveTypeMap.updateBy = $scope.loggedInUser.id;
                $scope.almEmpLeaveTypeMap.updateDate = DateUtils.convertLocaleDateToServer(new Date());
                console.log(JSON.stringify( $scope.almEmpLeaveTypeMap.employeeInfo));
                if ($scope.almEmpLeaveTypeMap.id != null) {
                    AlmEmpLeaveTypeMap.update($scope.almEmpLeaveTypeMap, onSaveFinished);
                    $rootScope.setWarningMessage('stepApp.almEmpLeaveTypeMap.updated');
                } else {
                    $scope.almEmpLeaveTypeMap.createBy = $scope.loggedInUser.id;
                    $scope.almEmpLeaveTypeMap.createDate = DateUtils.convertLocaleDateToServer(new Date());
                    AlmEmpLeaveTypeMap.save($scope.almEmpLeaveTypeMap, onSaveFinished);
                    $rootScope.setSuccessMessage('stepApp.almEmpLeaveTypeMap.created');
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



