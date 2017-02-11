'use strict';

angular.module('stepApp')
    .controller('EmployeeLoanApplicationPendingController',
        ['$scope','$stateParams','EmployeeLoanRequisitionForm', 'InstEmployeeByInstitute', 'FindLoanPendingDataForApprove','ParseLinks','Principal',
        function ($scope,$stateParams,EmployeeLoanRequisitionForm,InstEmployeeByInstitute,FindLoanPendingDataForApprove,ParseLinks,Principal) {
            $scope.employeeLoanRequisitionForms = [];
            $scope.page = 0;
            $scope.institute = [];
            $scope.instituteEmployee = [];
            $scope.instEmployeeIDs = [];

            $scope.loadAll = function() {
                FindLoanPendingDataForApprove.query({applyType:$stateParams.applyType},function(result){
                    console.log("applyType "+$stateParams.applyType);
                    $scope.employeeLoanRequisitionForms = result;
                });
            };

            $scope.loadPage = function(page) {
                $scope.page = page;
                $scope.loadAll();
            };
            $scope.loadAll();

    }]);
