'use strict';

angular.module('stepApp').controller('PfmsDeductionDialogController',
    ['$scope', '$rootScope', '$stateParams', '$state', 'entity', 'PfmsDeduction','PfmsLoanApplicationByEmployee', 'PfmsEmpMembership','PfmsEmpMembershipFormListByEmployee','PfmsEmpMembershipListByEmployee','PfmsLoanSchedule','PfmsLoanApplication', 'User', 'Principal', 'DateUtils',
        function($scope, $rootScope, $stateParams, $state, entity, PfmsDeduction, PfmsLoanApplicationByEmployee, PfmsEmpMembership, PfmsEmpMembershipFormListByEmployee, PfmsEmpMembershipListByEmployee,PfmsLoanSchedule, PfmsLoanApplication, User, Principal, DateUtils) {

            $scope.pfmsDeduction = entity;
            $scope.hremployeeinfos = [];
            $scope.pfmsEmpMembership = null;
            $scope.pfmsloanapplications = [];
            $scope.pfmsLoanScheduleList = [];

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

            $scope.loadEmployeeInfo = function(empInfo){
                $scope.empInfo = empInfo;
                $scope.designationName          = $scope.empInfo.designationInfo.designationInfo.designationName;
                $scope.departmentName           = $scope.empInfo.departmentInfo.departmentInfo.departmentName;
                $scope.dutySide                 = $scope.empInfo.workArea.typeName;
                $scope.nationality              = $scope.empInfo.nationality;
                $scope.fatherName               = $scope.empInfo.fatherName;
                $scope.loadPfAccountNo();
                $scope.loadPfmsLoanApp($scope.empInfo);
            };

            $scope.onChangeLoanApplication = function(){
                $scope.pfmsDeduction.installmentNo = $scope.pfmsDeduction.pfmsLoanApp.noOfInstallment;
                $scope.pfmsDeduction.deductionAmount = $scope.pfmsDeduction.pfmsLoanApp.loanAmount;
                $scope.loadPfmsEmpMembership();
            };

            $scope.monthList = {
                monthOptions: ['January','February','March','April', 'May','June','July','August','September','October','November','December']
            };

            $scope.isExitsData = true;
            //$scope.duplicateCheckByYearAndMonth = function(){
            //    $scope.isExitsData = true;
            //    PfmsDeduction.query(function(result){
            //        angular.forEach(result,function(dtoInfo){
            //            if(dtoInfo.employeeInfo.id == $scope.pfmsDeduction.employeeInfo.id
            //                && dtoInfo.deductionYear == $scope.pfmsDeduction.deductionYear
            //                && dtoInfo.deductionMonth == $scope.pfmsDeduction.deductionMonth){
            //                $scope.isExitsData = false;
            //            }
            //        });
            //    });
            //};

            if($stateParams.id){
                PfmsDeduction.get({id : $stateParams.id}, function(result) {
                    $scope.pfmsDeduction = result;
                    $scope.pfmsDeduction.deductionAmountPrev = $scope.pfmsDeduction.deductionAmount;
                    if(result.employeeInfo.id){
                        $scope.loadEmployeeInfo(result.employeeInfo);
                        $scope.loadPfmsEmpMembership();
                    }
                });
            }


            /*$scope.load = function(id) {
                PfmsDeduction.get({id : id}, function(result) {
                    $scope.pfmsDeduction = result;
                    console.log(JSON.stringify(result));
                    if(result.employeeInfo.id){
                        $scope.loadEmployeeInfo(result.employeeInfo);
                        $scope.loadPfmsEmpMembership();
                    }
                });
            };*/

            $scope.loadPfAccountNo= function()
            {
                PfmsEmpMembershipFormListByEmployee.query({employeeInfoId:  $scope.empInfo.id},function(result){
                    angular.forEach(result,function(dtoInfo){
                        $scope.pfmsDeduction.accountNo = dtoInfo.accountNo;
                    });
                });

            };

            $scope.loadPfmsEmpMembership= function(){

                PfmsEmpMembershipListByEmployee.query({employeeInfoId: $scope.empInfo.id},function(result){
                    angular.forEach(result,function(dtoInfo){
                            $scope.pfmsEmpMembership = dtoInfo;
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

            var month = new Array();
            month[0] = "January";
            month[1] = "February";
            month[2] = "March";
            month[3] = "April";
            month[4] = "May";
            month[5] = "June";
            month[6] = "July";
            month[7] = "August";
            month[8] = "September";
            month[9] = "October";
            month[10] = "November";
            month[11] = "December";

            var onSaveFinished = function (result) {
                $scope.$emit('stepApp:pfmsDeductionUpdate', result);
                if ($scope.pfmsDeduction.id != null) {
                    console.log($scope.pfmsEmpMembership.curOwnContributeTot+ ' pre '+ $scope.pfmsDeduction.deductionAmountPrev + ' tot '+ ($scope.pfmsEmpMembership.curOwnContributeTot + $scope.pfmsDeduction.deductionAmountPrev) - $scope.pfmsDeduction.deductionAmount);
                    $scope.pfmsEmpMembership.curOwnContributeTot = ($scope.pfmsEmpMembership.curOwnContributeTot + $scope.pfmsDeduction.deductionAmountPrev) - $scope.pfmsDeduction.deductionAmount;
                }else {
                    $scope.pfmsEmpMembership.curOwnContributeTot = $scope.pfmsEmpMembership.curOwnContributeTot - $scope.pfmsDeduction.deductionAmount;
                }
                PfmsEmpMembership.update($scope.pfmsEmpMembership, onSaveFinisedMem);
            };

            var onSaveFinisedMem = function(){
                $scope.isSaving = false;
                $state.go("pfmsDeduction");
            };

            $scope.save = function () {
                $scope.pfmsDeduction.updateBy = $scope.loggedInUser.id;
                $scope.pfmsDeduction.updateDate = DateUtils.convertLocaleDateToServer(new Date());
                if ($scope.pfmsDeduction.id != null) {
                    PfmsDeduction.update($scope.pfmsDeduction, onSaveFinished);
                    $rootScope.setSuccessMessage('stepApp.pfmsDeduction.updated');
                } else {
                    $scope.pfmsDeduction.createBy = $scope.loggedInUser.id;
                    $scope.pfmsDeduction.deductionDate = DateUtils.convertLocaleDateToServer(new Date());
                    $scope.pfmsDeduction.createDate = DateUtils.convertLocaleDateToServer(new Date());
                    PfmsDeduction.save($scope.pfmsDeduction, onSaveFinished);
                    $rootScope.setSuccessMessage('stepApp.pfmsDeduction.created');
                }
            };

            $scope.initiatePfmsLoanScheduleModel = function()
            {
                return {
                    loanYear: null,
                    loanMonth: null,
                    deductedAmount: null,
                    installmentNo: null,
                    totInstallNo: null,
                    totLoanAmount: null,
                    isRepay: false,
                    reason: null,
                    activeStatus: true
                };
            };

        }]);



