'use strict';

angular.module('stepApp')
    .controller('PfmsLoanApplicationController', function ($scope, $rootScope, PfmsLoanApplication, HrEmployeeInfo, PfmsLoanApplicationByEmployee, PfmsLoanApplicationSearch, ParseLinks) {
        $scope.pfmsLoanApplications = [];
       /* $scope.page = 0;
        $scope.loadAll = function() {
            PfmsLoanApplication.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.pfmsLoanApplications = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();*/


        $scope.newRequestList = [];
        $scope.loadingInProgress = true;
        $scope.requestEntityCounter = 0;
        var hrEmployeeInfoId = 0;

        HrEmployeeInfo.get({id: 'my'}, function (result) {
            hrEmployeeInfoId = result.id;
            $scope.loadAll();
        });


        $scope.loadAll = function()
        {
            $scope.requestEntityCounter = 1;
            $scope.newRequestList = [];
            PfmsLoanApplicationByEmployee.query({employeeId: hrEmployeeInfoId},function(result)
            {
                $scope.requestEntityCounter++;
                angular.forEach(result,function(dtoInfo)
                {
                    $scope.newRequestList.push(dtoInfo);
                });
            });
            $scope.newRequestList.sort($scope.sortById);
        };



        $scope.sortById = function(a,b){
            return b.id - a.id;
        };

        $scope.searchText = "";
        $scope.updateSearchText = "";

        $scope.clearSearchText = function (source)
        {
            if(source=='request')
            {
                $timeout(function(){
                    $('#searchText').val('');
                    angular.element('#searchText').triggerHandler('change');
                });
            }
        };


        $scope.delete = function (id) {
            PfmsLoanApplication.get({id: id}, function(result) {
                $scope.pfmsLoanApplication = result;
                $('#deletePfmsLoanApplicationConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            PfmsLoanApplication.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deletePfmsLoanApplicationConfirmation').modal('hide');
                    $scope.clear();
                    $rootScope.setErrorMessage('stepApp.pfmsLoanApplication.deleted');
                });
        };

        $scope.search = function () {
            PfmsLoanApplicationSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.pfmsLoanApplications = result;
            }, function(response) {
                if(response.status === 404) {
                    $scope.loadAll();
                }
            });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.pfmsLoanApplication = {
                timesOfWithdraw: null,
                loanAmount: null,
                noOfInstallment: null,
                reasonOfWithdrawal: null,
                isRepayFirstWithdraw: null,
                noOfInstallmentLeft: null,
                lastInstallmentReDate: null,
                applicationDate: null,
                activeStatus: null,
                createDate: null,
                createBy: null,
                updateDate: null,
                updateBy: null,
                id: null
            };
        };
    });
