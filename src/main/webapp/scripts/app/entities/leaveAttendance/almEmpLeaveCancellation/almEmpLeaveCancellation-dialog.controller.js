'use strict';

angular.module('stepApp').controller('AlmEmpLeaveCancellationDialogController',
    ['$scope', '$stateParams', '$state', 'entity', 'AlmEmpLeaveCancellation', 'User', 'Principal', 'DateUtils', 'HrEmployeeInfo', 'AlmEmpLeaveApplication',
        function($scope, $stateParams, $state, entity, AlmEmpLeaveCancellation, User, Principal, DateUtils, HrEmployeeInfo, AlmEmpLeaveApplication) {

            $scope.almEmpLeaveCancellation = entity;
            $scope.hremployeeinfos = [];
            $scope.almempleaveapplications1 = [];

            HrEmployeeInfo.query(function(result){
                angular.forEach(result,function(dtoInfo){
                    if(dtoInfo.activeStatus){
                        $scope.hremployeeinfos.push(dtoInfo);
                    }
                });
            });

            AlmEmpLeaveApplication.query(function(result){
                angular.forEach(result,function(dtoInfo){
                    if(dtoInfo.activeStatus){
                        $scope.almempleaveapplications1.push(dtoInfo);
                    }
                });
            });

            $scope.almempleaveapplications = [];

            HrEmployeeInfo.get({id: 'my'}, function (result) {

                $scope.hrEmployeeInfo = result;
                $scope.designationName      = $scope.hrEmployeeInfo.designationInfo.designationName;
                $scope.departmentName       = $scope.hrEmployeeInfo.departmentInfo.departmentName;
                $scope.workAreaName         = $scope.hrEmployeeInfo.workArea.typeName;

                $scope.leaveApplicationList();
            });

            $scope.isExitsData = true;
            $scope.exitsDataMessage = '';
            $scope.duplicateCheckByLeave = function(){
                $scope.isExitsData = true;
                $scope.exitsDataMessage = '';
                AlmEmpLeaveCancellation.query(function(result){
                    angular.forEach(result,function(dtoInfo){
                        if(dtoInfo.almEmpLeaveApplication.id == $scope.almEmpLeaveCancellation.almEmpLeaveApplication.id){
                            $scope.isExitsData = false;
                            $scope.exitsDataMessage = 'You Already applied for this leave.';
                        }
                    });
                });
            };

            $scope.leaveApplicationList = function(){
                AlmEmpLeaveApplication.query(function(result){
                    angular.forEach(result,function(dtoInfo)
                    {
                        if(dtoInfo.employeeInfo.id ==  $scope.hrEmployeeInfo.id && dtoInfo.applicationLeaveStatus == 'Approved'){
                            $scope.almempleaveapplications.push(dtoInfo);
                        }
                    });
                });
            };

            $scope.onChaneEmployeeLeaveApplication = function(){
                $scope.almLeaveType=        $scope.almEmpLeaveCancellation.almEmpLeaveApplication.almLeaveType;
                $scope.leaveFromDate=       $scope.almEmpLeaveCancellation.almEmpLeaveApplication.leaveFromDate;
                $scope.leaveToDate=         $scope.almEmpLeaveCancellation.almEmpLeaveApplication.leaveToDate  ;
                $scope.isHalfDayLeave=      $scope.almEmpLeaveCancellation.almEmpLeaveApplication.isHalfDayLeave ;
                $scope.halfDayLeaveInfo=    $scope.almEmpLeaveCancellation.almEmpLeaveApplication.halfDayLeaveInfo ;
                $scope.leaveDays=           $scope.almEmpLeaveCancellation.almEmpLeaveApplication.leaveDays ;
                $scope.reasonOfLeave=       $scope.almEmpLeaveCancellation.almEmpLeaveApplication.reasonOfLeave  ;
                $scope.contactNo=           $scope.almEmpLeaveCancellation.almEmpLeaveApplication.contactNo  ;
            };

            $scope.load = function(id) {
                AlmEmpLeaveCancellation.get({id : id}, function(result) {
                    $scope.almEmpLeaveCancellation = result;
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
                $scope.$emit('stepApp:almEmpLeaveCancellationUpdate', result);
                $scope.isSaving = false;
                $state.go("almEmpLeaveCancellation");
            };

            $scope.save = function () {
                $scope.almEmpLeaveCancellation.updateBy = $scope.loggedInUser.id;
                $scope.almEmpLeaveCancellation.updateDate = DateUtils.convertLocaleDateToServer(new Date());
                if ($scope.almEmpLeaveCancellation.id != null) {
                    AlmEmpLeaveCancellation.update($scope.almEmpLeaveCancellation, onSaveFinished);
                } else {
                    $scope.almEmpLeaveCancellation.createBy = $scope.loggedInUser.id;
                    $scope.almEmpLeaveCancellation.employeeInfo = $scope.hrEmployeeInfo;
                    $scope.almEmpLeaveCancellation.createDate = DateUtils.convertLocaleDateToServer(new Date());
                    $scope.almEmpLeaveCancellation.requestDate = DateUtils.convertLocaleDateToServer(new Date());
                    $scope.almEmpLeaveCancellation.cancelStatus = 'Pending';
                    AlmEmpLeaveCancellation.save($scope.almEmpLeaveCancellation, onSaveFinished);
                }
            };

        }]);

