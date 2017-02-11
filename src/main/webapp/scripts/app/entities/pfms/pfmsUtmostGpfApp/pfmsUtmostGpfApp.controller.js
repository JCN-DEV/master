'use strict';

angular.module('stepApp')
    .controller('PfmsUtmostGpfAppController', function ($scope, $rootScope, PfmsUtmostGpfApp,HrEmployeeInfo, PfmsUtmostGpfAppListByEmployee, PfmsUtmostGpfAppSearch, ParseLinks) {
        $scope.pfmsUtmostGpfApps = [];
        //$scope.page = 0;
        //$scope.loadAll = function() {
        //    PfmsUtmostGpfApp.query({page: $scope.page, size: 20}, function(result, headers) {
        //        $scope.links = ParseLinks.parse(headers('link'));
        //        $scope.pfmsUtmostGpfApps = result;
        //    });
        //};
        //$scope.loadPage = function(page) {
        //    $scope.page = page;
        //    $scope.loadAll();
        //};
        //$scope.loadAll();

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
            PfmsUtmostGpfAppListByEmployee.query({employeeInfoId: hrEmployeeInfoId},function(result)
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
            PfmsUtmostGpfApp.get({id: id}, function(result) {
                $scope.pfmsUtmostGpfApp = result;
                $('#deletePfmsUtmostGpfAppConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            PfmsUtmostGpfApp.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deletePfmsUtmostGpfAppConfirmation').modal('hide');
                    $scope.clear();
                    $rootScope.setErrorMessage('stepApp.pfmsUtmostGpfApp.deleted');
                });
        };

        $scope.search = function () {
            PfmsUtmostGpfAppSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.pfmsUtmostGpfApps = result;
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
            $scope.pfmsUtmostGpfApp = {
                prlDate: null,
                gpfNo: null,
                lastDeduction: null,
                deductionDate: null,
                billNo: null,
                billDate: null,
                tokenNo: null,
                tokenDate: null,
                withdrawFrom: null,
                applyDate: null,
                createDate: null,
                createBy: null,
                updateDate: null,
                updateBy: null,
                id: null
            };
        };
    });
