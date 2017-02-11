'use strict';

angular.module('stepApp').controller('PfmsLoanScheduleDialogController',
    ['$scope', '$rootScope','$stateParams', '$state', 'entity', 'PfmsLoanSchedule','PfmsLoanApplication','PfmsDeductionListByEmployee','PfmsLoanScheduleListByEmployee','PfmsEmpMembershipForm', 'User', 'Principal', 'DateUtils','HrEmployeeInfo',
        function($scope,$rootScope, $stateParams, $state, entity, PfmsLoanSchedule,PfmsLoanApplication, PfmsDeductionListByEmployee, PfmsLoanScheduleListByEmployee, PfmsEmpMembershipForm,  User, Principal, DateUtils, HrEmployeeInfo) {

            $scope.pfmsLoanSchedule = entity;
            //$scope.hremployeeinfos = HrEmployeeInfo.query();
            $scope.hremployeeinfos = [];
            $scope.pfmsEmpMembershipForms = [];
            $scope.pfmsloanapplications = [];

            PfmsEmpMembershipForm.query(function(result){
                angular.forEach(result,function(dtoInfo){
                    if(dtoInfo.activeStatus){
                        $scope.pfmsEmpMembershipForms.push(dtoInfo);
                        //$scope.hremployeeinfos.push(dtoInfo.employeeInfo);
                    }
                });
            });

            PfmsLoanApplication.query(function(result){
                angular.forEach(result,function(dtoInfo){
                    if(dtoInfo.approvalStatus == 'Approved' && dtoInfo.isRepayFirstWithdraw == false){
                        $scope.pfmsloanapplications.push(dtoInfo);
                        $scope.hremployeeinfos.push(dtoInfo.employeeInfo);
                    }
                });
            });

            $scope.isExitsData = true;
            $scope.duplicateDataMessage = '';
            $scope.duplicateScheduleCheckByYearAndMonth = function(){
                $scope.isExitsData = true;
                $scope.duplicateDataMessage = '';
                PfmsLoanScheduleListByEmployee.query({employeeInfoId: $scope.pfmsLoanSchedule.employeeInfo.id},function(result){
                    angular.forEach(result,function(dtoInfo){
                        if(dtoInfo.adjYear == $scope.pfmsLoanSchedule.adjYear
                            && dtoInfo.adjMonth == $scope.pfmsLoanSchedule.adjMonth){
                            $scope.isExitsData = false;
                            $scope.duplicateDataMessage = 'PF Loan is already schedule for this month.';
                        }
                    });
                });
            };

            $scope.loadEmployeeInfo = function(){
                $scope.empInfo = $scope.pfmsLoanSchedule.employeeInfo;
                $scope.designationName          = $scope.empInfo.designationInfo.designationInfo.designationName;
                $scope.departmentName           = $scope.empInfo.departmentInfo.departmentInfo.departmentName;
                $scope.dutySide                 = $scope.empInfo.workArea.typeName;
                $scope.nationality              = $scope.empInfo.nationality;
                $scope.fatherName               = $scope.empInfo.fatherName;
                $scope.onChangeEmplyeeInfo();
            };

            $scope.onChangeEmplyeeInfo = function(){
                PfmsDeductionListByEmployee.query({employeeInfoId: $scope.pfmsLoanSchedule.employeeInfo.id},function(result){
                    angular.forEach(result,function(dtoInfo){
                        $scope.pfmsloanapplications.push(dtoInfo.pfmsLoanApp);
                    });
                });
            };

            $scope.load = function(id) {
                PfmsLoanSchedule.get({id : id}, function(result) {
                    $scope.pfmsLoanSchedule = result;
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
                $scope.$emit('stepApp:pfmsLoanScheduleUpdate', result);
                $scope.isSaving = false;
                $state.go("pfmsLoanSchedule");
            };

            $scope.monthList = {
                monthOptions: ['January','February','March','April', 'May','June','July','August','September','October','November','December']
            };

            $scope.save = function () {
                $scope.pfmsLoanSchedule.updateBy = $scope.loggedInUser.id;
                $scope.pfmsLoanSchedule.updateDate = DateUtils.convertLocaleDateToServer(new Date());
                if ($scope.pfmsLoanSchedule.id != null) {
                    PfmsLoanSchedule.update($scope.pfmsLoanSchedule, onSaveFinished);
                    $rootScope.setSuccessMessage('stepApp.pfmsLoanSchedule.updated');
                } else {
                    $scope.pfmsLoanSchedule.isRepay = false;
                    $scope.pfmsLoanSchedule.createBy = $scope.loggedInUser.id;
                    $scope.pfmsLoanSchedule.createDate = DateUtils.convertLocaleDateToServer(new Date());
                    $scope.pfmsLoanSchedule.createDate = DateUtils.convertLocaleDateToServer(new Date());
                    PfmsLoanSchedule.save($scope.pfmsLoanSchedule, onSaveFinished);
                    $rootScope.setSuccessMessage('stepApp.pfmsLoanSchedule.created');
                }
            };

        }]);

