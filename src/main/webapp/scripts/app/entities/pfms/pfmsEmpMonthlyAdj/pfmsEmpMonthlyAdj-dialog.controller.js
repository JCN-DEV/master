'use strict';

angular.module('stepApp').controller('PfmsEmpMonthlyAdjDialogController',
    ['$scope', '$rootScope','$stateParams', '$state', 'entity', 'PfmsEmpMonthlyAdj','PfmsLoanApplicationByEmployee','PfmsLoanScheduleListByEmpAppYearMonth','PfmsLoanApplication','PfmsLoanSchedule','PfmsLoanScheduleListByEmployeeAndApp','PfmsDeductionListByEmployee','PfmsEmpMembershipForm', 'User', 'Principal', 'DateUtils','HrEmployeeInfo',
        function($scope, $rootScope, $stateParams, $state, entity, PfmsEmpMonthlyAdj,PfmsLoanApplicationByEmployee,PfmsLoanScheduleListByEmpAppYearMonth,PfmsLoanApplication, PfmsLoanSchedule, PfmsLoanScheduleListByEmployeeAndApp, PfmsDeductionListByEmployee,PfmsEmpMembershipForm, User, Principal, DateUtils, HrEmployeeInfo) {

            $scope.pfmsEmpMonthlyAdj = entity;
            //$scope.hremployeeinfos = HrEmployeeInfo.query();
            $scope.hremployeeinfos = [];
            $scope.pfmsEmpMembershipForms = [];
            $scope.pfmsloanapplications = [];
            $scope.pfmsLoanSchedule = null;


            PfmsLoanApplication.query(function(result){
                angular.forEach(result,function(dtoInfo){
                    if(dtoInfo.approvalStatus == 'Approved' && dtoInfo.isRepayFirstWithdraw == false){
                        $scope.hremployeeinfos.push(dtoInfo.employeeInfo);
                    }
                });
            });

            $scope.loadPfmsLoanApp = function(empInfo){
                $scope.pfmsloanapplications = [];
                PfmsLoanApplicationByEmployee.query({employeeId: empInfo.id},function(result){
                    angular.forEach(result,function(dtoInfo){
                        if(dtoInfo.approvalStatus == 'Approved' && dtoInfo.isRepayFirstWithdraw == false){
                            $scope.pfmsloanapplications.push(dtoInfo);
                        }
                    });
                });

            };

            if($stateParams.id){
                PfmsEmpMonthlyAdj.get({id : $stateParams.id}, function(result) {
                    $scope.pfmsEmpMonthlyAdj= result;
                    $scope.loadEmployeeInfo();
                });

            }

            $scope.loadEmployeeInfo = function(){
                $scope.empInfo = $scope.pfmsEmpMonthlyAdj.employeeInfo;
                $scope.designationName          = $scope.empInfo.designationInfo.designationInfo.designationName;
                $scope.departmentName           = $scope.empInfo.departmentInfo.departmentInfo.departmentName;
                $scope.dutySide                 = $scope.empInfo.workArea.typeName;
                $scope.nationality              = $scope.empInfo.nationality;
                $scope.fatherName               = $scope.empInfo.fatherName;
                $scope.onChangeEmplyeeInfo();
                $scope.loadPfmsLoanApp($scope.empInfo);
            };

            $scope.onChangeEmplyeeInfo = function(){
                PfmsDeductionListByEmployee.query({employeeInfoId: $scope.pfmsEmpMonthlyAdj.employeeInfo.id},function(result){
                    angular.forEach(result,function(dtoInfo){
                        $scope.pfmsloanapplications.push(dtoInfo.pfmsLoanApp);
                    });
                });
            };

            $scope.isDeductedAmount = true;
            $scope.isDeductedAmountPaid = true;
            $scope.deductedAmountMsg = '';
            $scope.deductedAmountPaidMsg = '';
            $scope.loadDeductedAmount = function(){
                $scope.isDeductedAmountPaid = true;
                $scope.deductedAmountPaidMsg = '';
                $scope.pfmsEmpMonthlyAdj.deductedAmount = 0;
                $scope.pfmsLoanSchedule = null;
                if($scope.pfmsEmpMonthlyAdj.adjYear != null && $scope.pfmsEmpMonthlyAdj.adjMonth != null){
                    PfmsLoanScheduleListByEmpAppYearMonth.query({
                        employeeInfoId: $scope.pfmsEmpMonthlyAdj.employeeInfo.id,
                        pfmsLoanAppId : $scope.pfmsEmpMonthlyAdj.pfmsLoanApp.id,
                        loanYear: $scope.pfmsEmpMonthlyAdj.adjYear,
                        loanMonth: $scope.pfmsEmpMonthlyAdj.adjMonth
                    },function(result){
                        angular.forEach(result,function(dtoInfo){
                            $scope.pfmsLoanSchedule = dtoInfo;
                             if(dtoInfo && dtoInfo.isRepay == true ){
                                $scope.isDeductedAmountPaid = false;
                                $scope.deductedAmountPaidMsg = 'The Deducted amount is already being Paid.';
                            }else if(dtoInfo && dtoInfo.isRepay == false){
                                $scope.pfmsEmpMonthlyAdj.deductedAmount = dtoInfo.deductedAmount;
                            }
                        });
                        $scope.noAmountFound();
                    });
                }

            };

            $scope.noAmountFound = function(){
                $scope.isDeductedAmount = true;
                $scope.deductedAmountMsg = '';
                if($scope.pfmsLoanSchedule == null){
                    $scope.isDeductedAmount = false;
                    $scope.deductedAmountMsg = 'No amount found to adjust.';
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
            $scope.getLoggedInUser();

            var onSaveFinished = function (result) {
                $scope.$emit('stepApp:pfmsEmpMonthlyAdjUpdate', result);
                $scope.pfmsLoanSchedule.deductedAmount =  $scope.pfmsEmpMonthlyAdj.modifiedAmount;
                PfmsLoanSchedule.update($scope.pfmsLoanSchedule, onAmountUpdate);
            };

            var onAmountUpdate = function (result) {
                $scope.isSaving = false;
                $state.go("pfmsEmpMonthlyAdj");
            };

            $scope.save = function () {
                $scope.pfmsEmpMonthlyAdj.updateBy = $scope.loggedInUser.id;
                $scope.pfmsEmpMonthlyAdj.updateDate = DateUtils.convertLocaleDateToServer(new Date());
                if ($scope.pfmsEmpMonthlyAdj.id != null) {
                    PfmsEmpMonthlyAdj.update($scope.pfmsEmpMonthlyAdj, onSaveFinished);
                    $rootScope.setWarningMessage('stepApp.pfmsEmpMonthlyAdj.updated');

                } else {
                    $scope.pfmsEmpMonthlyAdj.createBy = $scope.loggedInUser.id;
                    $scope.pfmsEmpMonthlyAdj.createDate = DateUtils.convertLocaleDateToServer(new Date());
                    PfmsEmpMonthlyAdj.save($scope.pfmsEmpMonthlyAdj, onSaveFinished);
                    $rootScope.setSuccessMessage('stepApp.pfmsEmpMonthlyAdj.created');

                }
            };

            $scope.monthList = {
                monthOptions: ['January','February','March','April', 'May','June','July','August','September','October','November','December']
            };

        }]);


