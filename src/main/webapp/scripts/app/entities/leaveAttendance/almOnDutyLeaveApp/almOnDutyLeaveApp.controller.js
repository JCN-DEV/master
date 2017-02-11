'use strict';

angular.module('stepApp')
    .controller('AlmOnDutyLeaveAppController',
    ['$scope','HrEmployeeInfo', 'AlmOnDutyLeaveApp', 'AlmOnDutyLeaveAppSearch', 'ParseLinks',
    function ($scope,HrEmployeeInfo, AlmOnDutyLeaveApp, AlmOnDutyLeaveAppSearch, ParseLinks) {
        $scope.almOnDutyLeaveApps = [];
        $scope.page = 0;


        HrEmployeeInfo.get({id: 'my'}, function (result) {
            $scope.hrEmployeeInfo = result;

        });


        $scope.loadAll = function()
        {
            $scope.requestEntityCounter = 1;
            $scope.newRequestList = [];
            AlmOnDutyLeaveApp.query(function(result)
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


       /* $scope.loadAll = function() {
            AlmOnDutyLeaveApp.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.almOnDutyLeaveApps = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();*/

        $scope.delete = function (id) {
            AlmOnDutyLeaveApp.get({id: id}, function(result) {
                $scope.almOnDutyLeaveApp = result;
                $('#deleteAlmOnDutyLeaveAppConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            AlmOnDutyLeaveApp.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteAlmOnDutyLeaveAppConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            AlmOnDutyLeaveAppSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.almOnDutyLeaveApps = result;
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
            $scope.almOnDutyLeaveApp = {
                applicationDate: null,
                dutyDate: null,
                dutyInTimeHour: null,
                dutyInTimeMin: null,
                dutyOutTimeHour: null,
                dutyOutTimeMin: null,
                endDutyDate: null,
                reason: null,
                activeStatus: false,
                createDate: null,
                createBy: null,
                updateDate: null,
                updateBy: null,
                id: null
            };
        };
    }]);
