'use strict';

angular.module('stepApp').controller('PfmsLoanApplicationDialogController',
    ['$scope','$rootScope', '$stateParams', '$state', 'entity', 'PfmsLoanApplication','PfmsLoanApplicationByEmployee','PfmsEmpMembershipListByEmployee','PfmsEmpMonthlyAdj', 'User', 'Principal', 'DateUtils','HrEmployeeInfo',
        function($scope,$rootScope, $stateParams, $state, entity, PfmsLoanApplication, PfmsLoanApplicationByEmployee, PfmsEmpMembershipListByEmployee, PfmsEmpMonthlyAdj, User, Principal, DateUtils, HrEmployeeInfo) {

            $scope.pfmsLoanApplication = entity;
            //$scope.hremployeeinfos = HrEmployeeInfo.query();
            $scope.load = function(id) {
                PfmsLoanApplication.get({id : id}, function(result) {
                    $scope.pfmsLoanApplication = result;
                });
            };

            if($stateParams.id){
                PfmsLoanApplication.get({id : $stateParams.id}, function(result) {
                    $scope.pfmsLoanApplication= result;
                });

            }

            HrEmployeeInfo.get({id: 'my'}, function (result) {

                $scope.hrEmployeeInfo = result;
                $scope.designationName          = $scope.hrEmployeeInfo.designationInfo.designationInfo.designationName;
                $scope.departmentName           = $scope.hrEmployeeInfo.departmentInfo.departmentInfo.departmentName;
                $scope.dutySide                 = $scope.hrEmployeeInfo.workArea.typeName;
                $scope.nationality              = $scope.hrEmployeeInfo.nationality;
                $scope.fatherName               = $scope.hrEmployeeInfo.fatherName;
                //$scope.loadGroupByEmployee();
            });

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

            $scope.isSeventyFiveParcentCheck = true;
            $scope.isSeventyFiveParcentCheckEmpty = true;
            $scope.seventyFivePercentCheckMessage = '';
            $scope.seventyFivePercentCheckMessageEmpty = '';
            $scope.seventyFivePercentCheck = function(){
                $scope.isSeventyFiveParcentCheck = true;
                $scope.pendingLoanCheck();
                $scope.timesOfWithdrawalChck();
                $scope.timesOfWithdrawalTwoChck();
                $scope.seventyFivePercentCheckMessage = '';
                PfmsEmpMembershipListByEmployee.query({employeeInfoId: $scope.hrEmployeeInfo.id},function(result){
                    if(result.length){
                        angular.forEach(result,function(dtoInfo){
                            $scope.curTotSeventyFiveParcent = (dtoInfo.curOwnContributeTot * 75) / 100;
                            if($scope.pfmsLoanApplication.loanAmount > $scope.curTotSeventyFiveParcent){
                                $scope.isSeventyFiveParcentCheck = false;
                                $scope.seventyFivePercentCheckMessage = 'You can not take more than 75 Percent of your current PF amount.';
                            }
                        });
                    }else{
                        $scope.isSeventyFiveParcentCheckEmpty = false;
                        $scope.seventyFivePercentCheckMessageEmpty = 'You do not have any PF amount.';
                    }

                });
            };


            $scope.isTimeOfWithDrawCheck = true;
            $scope.timesOfWithdrawalCheckMessage = '';
            $scope.timesOfWithdrawalChck = function(){
                $scope.isTimeOfWithDrawCheck = true;
                $scope.timesOfWithdrawalCheckMessage = '';
                PfmsLoanApplicationByEmployee.query({employeeId: $scope.hrEmployeeInfo.id}, function(result){
                    var timesOfWithdraw = 0;
                    angular.forEach(result,function(dtoInfo){
                        if(dtoInfo.isRepayFirstWithdraw){
                            timesOfWithdraw +=1;
                            if(timesOfWithdraw > 3){
                                $scope.isTimeOfWithDrawCheck = false;
                                $scope.timesOfWithdrawalCheckMessage = 'You can not take more than three Loan at a time.';
                            }
                        }
                    });
                });
            };

            $scope.isTimeOfWithDrawTwoCheck = true;
            $scope.timesOfWithdrawalTwoCheckMessage = '';
            $scope.timesOfWithdrawalTwoChck = function(){
                $scope.isTimeOfWithDrawTwoCheck = true;
                $scope.timesOfWithdrawalTwoCheckMessage = '';
                PfmsLoanApplicationByEmployee.query({employeeId: $scope.hrEmployeeInfo.id}, function(result){
                    var timesOfWithdraw = 0;
                    angular.forEach(result,function(dtoInfo){
                        if(dtoInfo.isRepayFirstWithdraw) {
                            timesOfWithdraw += 1;
                            if (timesOfWithdraw == 2) {
                                $scope.isTimeOfWithDrawTwoCheck = false;
                                $scope.timesOfWithdrawalTwoCheckMessage = 'You can take loan one more time.';
                            }
                        }
                    });
                });
            };

            $scope.isPendingLoan = true;
            $scope.pendingLoanCheckMessage = '';
            $scope.pendingLoanCheck = function(){
                $scope.isPendingLoan = true;
                $scope.pendingLoanCheckMessage = '';
                PfmsLoanApplicationByEmployee.query({employeeId: $scope.hrEmployeeInfo.id}, function(result){
                    angular.forEach(result,function(dtoInfo){
                        if(dtoInfo.isDisburseLoan == false && dtoInfo.approvalStatus == 'Approved'){
                        //    $scope.isPendingLoan = false;
                          //  $scope.pendingLoanCheckMessage = 'You already have a pending Loan waiting for disburse.';
                        }
                    });
                });
            };

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
                $scope.$emit('stepApp:pfmsLoanApplicationUpdate', result);
                $scope.isSaving = false;
                $state.go("pfmsLoanApplication");
            };

            $scope.save = function () {
                $scope.pfmsLoanApplication.updateBy = $scope.loggedInUser.id;
                $scope.pfmsLoanApplication.updateDate = DateUtils.convertLocaleDateToServer(new Date());
                if ($scope.pfmsLoanApplication.id != null) {
                    PfmsLoanApplication.update($scope.pfmsLoanApplication, onSaveFinished);
                    $rootScope.setSuccessMessage('stepApp.pfmsLoanApplication.updated');
                } else {
                    $scope.pfmsLoanApplication.employeeInfo = $scope.hrEmployeeInfo;
                    $scope.pfmsLoanApplication.isRepayFirstWithdraw = false;
                    $scope.pfmsLoanApplication.isDisburseLoan = false;
                    $scope.pfmsLoanApplication.createBy = $scope.loggedInUser.id;
                    $scope.pfmsLoanApplication.createDate = DateUtils.convertLocaleDateToServer(new Date());
                    $scope.pfmsLoanApplication.applicationDate = DateUtils.convertLocaleDateToServer(new Date());
                    $scope.pfmsLoanApplication.approvalStatus = 'Pending';
                    $scope.pfmsLoanApplication.activeStatus = true;
                    PfmsLoanApplication.save($scope.pfmsLoanApplication, onSaveFinished);
                    $rootScope.setSuccessMessage('stepApp.pfmsLoanApplication.created');
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

        }]);



