'use strict';

angular.module('stepApp')
    .controller('EmployeeLoanRequisitionFormController',
        function ($scope, EmployeeLoanRequisitionForm, EmployeeLoanRequisitionFormSearch,CurrentHrEmployeeInfo,
                  Principal,searchLoanRequisitionDataByHrEmpID,ParseLinks,CheckEmployeeEligibleForLoanApplication) {
        $scope.employeeLoanRequisitionForms = [];
        $scope.page = 0;
        $scope.employee = [];
        $scope.editLoanForm = true;
        $scope.loanApplyButton = true;

        if(Principal.hasAnyAuthority(['ROLE_INSTEMP']) ||  Principal.hasAnyAuthority(['ROLE_USER'])){
            CurrentHrEmployeeInfo.get({},function(result){
                $scope.employee = result;
                 if(result.id != null){
                     searchLoanRequisitionDataByHrEmpID.query({employeeInfoID:result.id},function(data){
                         $scope.employeeLoanRequisitionForms = data;
                     }, function(response) {
                        if(response.status === 404) {
                            $scope.loadAll();
                        }
                     });
                 }
                CheckEmployeeEligibleForLoanApplication.query({employeeInfoID:result.id},function(res){
                    console.log('-------------');
                        console.log(res[0]);
                        //$scope.loanApplyButton = false;
                }, function(response) {
                    if(response.status === 404) {
                        $scope.loanApplyButton = true;
                    }
                });
           });
        }

        if(Principal.hasAnyAuthority(['ROLE_ADMIN'])){
              console.log('--------------');
              EmployeeLoanRequisitionForm.query({page: $scope.page, size: 20}, function(result, headers) {
                  $scope.links = ParseLinks.parse(headers('link'));
                  $scope.employeeLoanRequisitionForms = result;
              });
        }



        $scope.loadAll = function() {
//             console.log('--------------');
//            EmployeeLoanRequisitionForm.query({page: $scope.page, size: 20}, function(result, headers) {
//                $scope.links = ParseLinks.parse(headers('link'));
//                $scope.employeeLoanRequisitionForms = result;
//            });
    };


        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            EmployeeLoanRequisitionForm.get({id: id}, function(result) {
                $scope.employeeLoanRequisitionForm = result;
                $('#deleteEmployeeLoanRequisitionFormConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            EmployeeLoanRequisitionForm.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteEmployeeLoanRequisitionFormConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {

        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.employeeLoanRequisitionForm = {
                loanRequisitionCode: null,
                employeeInfo: null,
                amount: null,
                installment: null,
                approveStatus: null,
                createDate: null,
                createBy: null,
                updateDate: null,
                updateBy: null,
                id: null
            };
        };
    });
