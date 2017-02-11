'use strict';

angular.module('stepApp')
    .controller('EmployeeLoanApprovedListController',
        function ($scope,Principal,FindLoanRequisitionByApproveStatus) {

        $scope.employeeLoanRequisitionForms = [];
        $scope.page = 0;
        $scope.employee = [];
        $scope.editLoanForm = true;

        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };

        if(Principal.hasAnyAuthority(['ROLE_ADMIN'])){
            console.log('------------');
            FindLoanRequisitionByApproveStatus.query({approveStatus:5},function(result){
                   //  console.log('------------');
                $scope.employeeLoanRequisitionForms = result;
            });
        }

    });
