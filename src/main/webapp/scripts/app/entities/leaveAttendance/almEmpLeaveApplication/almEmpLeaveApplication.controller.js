'use strict';

angular.module('stepApp')
    .controller('AlmEmpLeaveApplicationController',
    ['$scope', 'HrEmployeeInfo', 'AlmEmpLeaveApplication', 'AlmEmpLeaveApplicationSearch', 'ParseLinks',
    function ($scope,HrEmployeeInfo, AlmEmpLeaveApplication, AlmEmpLeaveApplicationSearch, ParseLinks) {

        $scope.newRequestList = [];
        $scope.loadingInProgress = true;
        $scope.requestEntityCounter = 0;

        HrEmployeeInfo.get({id: 'my'}, function (result) {
            $scope.hrEmployeeInfo = result;

        });


        $scope.loadAll = function()
        {
            $scope.requestEntityCounter = 1;
            $scope.newRequestList = [];
            AlmEmpLeaveApplication.query(function(result)
            {
                $scope.requestEntityCounter++;
                angular.forEach(result,function(dtoInfo)
                {
                    if(dtoInfo.employeeInfo.id == $scope.hrEmployeeInfo.id){
                        $scope.newRequestList.push(dtoInfo);
                    }
                });
            });
            $scope.newRequestList.sort($scope.sortById);
        };

        $scope.loadAll();

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





        /*$scope.almEmpLeaveApplications = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            AlmEmpLeaveApplication.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.almEmpLeaveApplications = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();*/

        $scope.delete = function (id) {
            AlmEmpLeaveApplication.get({id: id}, function(result) {
                $scope.almEmpLeaveApplication = result;
                $('#deleteAlmEmpLeaveApplicationConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            AlmEmpLeaveApplication.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteAlmEmpLeaveApplicationConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            AlmEmpLeaveApplicationSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.almEmpLeaveApplications = result;
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
            $scope.almEmpLeaveApplication = {
                applicationDate: null,
                applicationLeaveStatus: null,
                leaveFromDate: null,
                leaveToDate: null,
                isHalfDayLeave: null,
                halfDayLeaveInfo: null,
                reasonOfLeave: null,
                contactNo: null,
                isWithCertificate: null,
                assignResposibilty: null,
                paymentType: null,
                activeStatus: false,
                createDate: null,
                createBy: null,
                updateDate: null,
                updateBy: null,
                id: null
            };
        };
    }]);
