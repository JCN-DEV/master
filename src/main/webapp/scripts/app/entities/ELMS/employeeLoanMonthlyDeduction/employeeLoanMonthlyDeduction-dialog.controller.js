'use strict';

angular.module('stepApp').controller('EmployeeLoanMonthlyDeductionDialogController',
    ['$scope', '$stateParams', '$rootScope', '$q', 'entity', 'EmployeeLoanMonthlyDeduction', 'InstEmployee', 'EmployeeLoanRequisitionForm','findLoanRequisitionDataByHrEmpID',
                'HrEmployeeInfoByEmployeeId','FindBillRegisterByBillNo','FindCheckRegisterByCheckNumber','GetApproveAmountByReqCode',
        function($scope, $stateParams, $rootScope, $q, entity, EmployeeLoanMonthlyDeduction, InstEmployee, EmployeeLoanRequisitionForm,findLoanRequisitionDataByHrEmpID,
                 HrEmployeeInfoByEmployeeId,FindBillRegisterByBillNo,FindCheckRegisterByCheckNumber,GetApproveAmountByReqCode) {

        $scope.employeeLoanMonthlyDeduction = entity;
        $scope.employeeLoanRequisitionForm = [];
        $scope.hrEmployeeInfo = [];
        $scope.empInfoDiv = true;
        $scope.employeeloanrequisitionforms = EmployeeLoanRequisitionForm.query({filter: 'employeeloanmonthlydeduction-is-null'});
        $scope.loanRequisitionCode = '';
        $scope.employeeName = '';
        $scope.employeeDesignation = '';
        $scope.employeeDept = '';
        $scope.errorForBillRegister = false;
        $scope.errorForCheckRegister = false;
        $scope.errorForLoanAmount = false;

        $scope.load = function(id) {
            EmployeeLoanMonthlyDeduction.get({id : id}, function(result) {
                $scope.employeeLoanMonthlyDeduction = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('stepApp:employeeLoanMonthlyDeductionUpdate', result);
            $state.go('employeeLoanInfo.employeeLoanMonthlyDeduction',{},{reload:true});
           //  $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.employeeLoanMonthlyDeduction.id != null) {
                EmployeeLoanMonthlyDeduction.update($scope.employeeLoanMonthlyDeduction, onSaveFinished);
                $rootScope.setWarningMessage('stepApp.employeeLoanMonthlyDeduction.updated');
            } else {
                $scope.employeeLoanMonthlyDeduction.employeeInfo = $scope.hrEmployeeInfo;
                $scope.employeeLoanMonthlyDeduction.loanRequisitionForm = $scope.employeeLoanRequisitionForm;
                EmployeeLoanMonthlyDeduction.save($scope.employeeLoanMonthlyDeduction, onSaveFinished);
                $rootScope.setSuccessMessage('stepApp.employeeLoanMonthlyDeduction.created');
            }
        };

        $scope.clear = function() {
           //  $modalInstance.dismiss('cancel');
        };

        $scope.searchEmployee = function(){
            HrEmployeeInfoByEmployeeId.get({id: $scope.employeeLoanMonthlyDeduction.employeeId},function(result){
                $scope.hrEmployeeInfo = result;
                $scope.employeeName = result.fullName;
                $scope.employeeDept = result.departmentInfo.departmentInfo.departmentName;
                $scope.employeeDesignation = result.designationInfo.designationInfo.designationName;

                findLoanRequisitionDataByHrEmpID.get({employeeInfoId: $scope.hrEmployeeInfo.id, approveStatus:5},function(data){
                    $scope.employeeLoanRequisitionForm = data;
                    $scope.loanRequisitionCode = data.loanRequisitionCode;
                    $scope.applyDate = data.createDate;
                    $scope.empInfoDiv = false;
                });
            });
        };

        $scope.findBillRegister = function(){
            FindBillRegisterByBillNo.get({billNo:$scope.employeeLoanMonthlyDeduction.billNo},function(result){

                if(result.employeeInfo.employeeId == $scope.employeeLoanMonthlyDeduction.employeeId){
                    console.log(result);
                    $scope.employeeLoanMonthlyDeduction.loanBillRegister =  result;
                }else{
                    $scope.errorForBillRegister = true;
                }
            });
        };
        $scope.findCheckRegister = function(){
            FindCheckRegisterByCheckNumber.get({checkNumber:$scope.employeeLoanMonthlyDeduction.checkNumber},function(result){
                if(result.employeeInfo.employeeId == $scope.employeeLoanMonthlyDeduction.employeeId ){
                     console.log(result);
                     $scope.employeeLoanMonthlyDeduction.loanCheckRegister =  result;
                }else {
                    $scope.errorForCheckRegister = true;
                }
            });
        };

        // This function will check total Loan amount is equal to Loan Amount approved By DG

        $scope.ValidateTotalLoanAmount = function(){

            GetApproveAmountByReqCode.get({loanReqCode:$scope.employeeLoanRequisitionForm},function(result){
                if(result.approvedLoanAmount == $scope.employeeLoanMonthlyDeduction.totalLoanAmountApproved){
                    $scope.errorForLoanAmount = false;
                }else{
                    $scope.errorForLoanAmount = true;
                }
            });

        }

}]).directive('monthOptions', function() {
        return {
            restrict: 'A',
            template:
                '<option value="1">January</option>' +
                '<option value="2">February</option>' +
                '<option value="3">March</option>' +
                '<option value="4">April</option>' +
                '<option value="5">May</option>' +
                '<option value="6">June</option>' +
                '<option value="7">July</option>' +
                '<option value="8">August</option>' +
                '<option value="9">September</option>' +
                '<option value="10">October</option>' +
                '<option value="11">November</option>' +
                '<option value="12">December</option>'
        }
    });
