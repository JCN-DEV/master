'use strict';

angular.module('stepApp')
    .controller('HrEmpActingDutyInfoController',
    ['$rootScope','$scope', '$state', 'DataUtils', 'HrEmpActingDutyInfo', 'HrEmpActingDutyInfoSearch', 'ParseLinks',
    function ($rootScope,$scope, $state, DataUtils, HrEmpActingDutyInfo, HrEmpActingDutyInfoSearch, ParseLinks) {

        $scope.hrEmpActingDutyInfos = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 0;
        $scope.stateName = "hrEmpActingDutyInfo";
        $scope.loadAll = function()
        {
            if($rootScope.currentStateName == $scope.stateName){
                $scope.page = $rootScope.pageNumber;
            }
            else {
                $rootScope.pageNumber = $scope.page;
                $rootScope.currentStateName = $scope.stateName;
            }
            HrEmpActingDutyInfo.query({page: $scope.page, size: 5000, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.hrEmpActingDutyInfos = result;
            });
        };
        $scope.loadPage = function(page)
        {
            $rootScope.currentStateName = $scope.stateName;
            $rootScope.pageNumber = page;
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            HrEmpActingDutyInfoSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.hrEmpActingDutyInfos = result;
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
            $scope.hrEmpActingDutyInfo = {
                toInstitution: null,
                designation: null,
                toDepartment: null,
                fromDate: null,
                toDate: null,
                officeOrderNumber: null,
                officeOrderDate: null,
                workOnActingDuty: null,
                comments: null,
                goDate: null,
                goDoc: null,
                goDocContentType: null,
                goDocName: null,
                goNumber:null,
                logId:null,
                logStatus:null,
                logComments:null,
                activeStatus: true,
                createDate: null,
                createBy: null,
                updateDate: null,
                updateBy: null,
                id: null
            };
        };

        $scope.abbreviate = DataUtils.abbreviate;

        $scope.byteSize = DataUtils.byteSize;
    }]);
