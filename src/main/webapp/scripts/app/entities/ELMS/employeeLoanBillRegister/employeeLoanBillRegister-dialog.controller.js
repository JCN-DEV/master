'use strict';

angular.module('stepApp')
    .controller('EmployeeLoanBillRegisterDialogController',
        ['$scope','$state', '$rootScope', '$stateParams' ,'$filter','entity', 'EmployeeLoanBillRegister', 'InstEmployee', 'EmployeeLoanRequisitionForm',
        'findLoanRequisitionDataByHrEmpID','HrEmployeeInfoByEmployeeId','EmployeeLoanCheckRegister','FindBillRegisterByLoanRequiID',
        function($scope,$state, $rootScope, $stateParams,$filter ,entity, EmployeeLoanBillRegister, InstEmployee, EmployeeLoanRequisitionForm,
        findLoanRequisitionDataByHrEmpID,HrEmployeeInfoByEmployeeId,EmployeeLoanCheckRegister,FindBillRegisterByLoanRequiID) {

        $scope.employeeLoanBillRegister = entity;
        $scope.empInfoDiv = true;
        $scope.billRegisterShow = false;
        $scope.checkRegisterShow = false;
        $scope.hrEmployeeInfo = [];
        $scope.employeeLoanRequisitionForm = [];
        $scope.employeeLoanCheckRegister = entity;
        $scope.loanRequisitionCode = '';
        $scope.employeeName = '';
        $scope.employeeDesignation = '';
        $scope.employeeDept = '';
        $scope.formDiv = true;
        $scope.checkFormDiv = true;
        $scope.issueDateError = false;
        $scope.issueDateInvalid = true;

        $scope.load = function(id) {
            EmployeeLoanBillRegister.get({id : id}, function(result) {
                $scope.employeeLoanBillRegister = result;
            });
        };
        $scope.registerShow = function(value){
            if(value == 'billRegister'){
              $scope.checkFormDiv = true;
              $scope.formDiv = true;
              $scope.empInfoDiv = true;
              $scope.billRegisterShow = true;
              $scope.checkRegisterShow = false;

            }if(value == 'checkRegister'){
                $scope.checkFormDiv = true;
                $scope.formDiv = true;
                $scope.empInfoDiv = true;
                $scope.billRegisterShow = false;
                $scope.checkRegisterShow = true;
            }
        }

        $scope.issueDateCheck = function(){
             if($filter('date')(new Date($scope.employeeLoanCheckRegister.issueDate), 'yyyy-MM-dd') <  $scope.employeeLoanBillRegister.issueDate){
               $scope.issueDateError = true;
               $scope.issueDateInvalid = true;
              }else{
                $scope.issueDateError = false;
                $scope.issueDateInvalid = false;
              }
        }

        var onSaveFinished = function (result) {
             $state.go('employeeLoanInfo.employeeLoanBillRegister',{},{reload:true});
        };

        $scope.save = function () {
            if ($scope.employeeLoanBillRegister.id != null) {
                EmployeeLoanBillRegister.update($scope.employeeLoanBillRegister, onSaveFinished);
                $rootScope.setWarningMessage('stepApp.employeeLoanBillRegister.updated');
            } else {
                $scope.employeeLoanBillRegister.applicationType = $scope.employeeLoanRegister.applicationType
                $scope.employeeLoanBillRegister.employeeInfo = $scope.hrEmployeeInfo;
                $scope.employeeLoanBillRegister.loanRequisitionForm = $scope.employeeLoanRequisitionForm;
                EmployeeLoanBillRegister.save($scope.employeeLoanBillRegister, onSaveFinished);
                $rootScope.setSuccessMessage('stepApp.employeeLoanBillRegister.created');
            }
        };

        // This Code to save Check Register
        $scope.saveEmployeeCheckRegister = function(){
            if ($scope.employeeLoanCheckRegister.id != null) {
                EmployeeLoanCheckRegister.update($scope.employeeLoanCheckRegister, onSaveFinished);
            } else {
                $scope.employeeLoanCheckRegister.applicationType = $scope.employeeLoanRegister.applicationType;
                $scope.employeeLoanCheckRegister.loanBillRegister =  $scope.employeeLoanBillRegister;
                $scope.employeeLoanCheckRegister.employeeInfo = $scope.hrEmployeeInfo;
                $scope.employeeLoanCheckRegister.loanRequisitionForm = $scope.employeeLoanRequisitionForm;
                EmployeeLoanCheckRegister.save($scope.employeeLoanCheckRegister, onSaveFinished);
            }
        };

        $scope.clear = function() {

        };

        $scope.searchEmployee = function(){
             $scope.formDiv = true;
             $scope.checkFormDiv = true;
             $scope.empInfoDiv = true;

            HrEmployeeInfoByEmployeeId.get({id: $scope.employeeLoanRegister.employeeId},function(hrEmployeeData){
                $scope.hrEmployeeInfo = hrEmployeeData;
                $scope.employeeName = hrEmployeeData.fullName;
                $scope.employeeDept = hrEmployeeData.departmentInfo.departmentInfo.departmentName;
                $scope.employeeDesignation = hrEmployeeData.designationInfo.designationInfo.designationName;

                findLoanRequisitionDataByHrEmpID.get({ employeeInfoId: $scope.hrEmployeeInfo.id, approveStatus:5 },function(result){
                    $scope.employeeLoanRequisitionForm = result;
                    $scope.loanRequisitionCode = result.loanRequisitionCode;
                    $scope.applyDate = result.createDate;
                    if($scope.loanRequisitionCode.length > 0){
                        if($scope.checkRegisterShow){

                            FindBillRegisterByLoanRequiID.get({loanRequisitionID:$scope.employeeLoanRequisitionForm.id},function(data){
                                $scope.employeeLoanBillRegister = data;
                                if(data.billNo){
                                    $scope.checkFormDiv = false;
                                    $scope.formDiv = true;
                                    $scope.empInfoDiv = false;
                                }
                            });
                        }else{
                            $scope.formDiv = false;
                            $scope.empInfoDiv = false;
                            $scope.checkFormDiv = true;
                        }
                    }
                });
            });
        }
}]);
