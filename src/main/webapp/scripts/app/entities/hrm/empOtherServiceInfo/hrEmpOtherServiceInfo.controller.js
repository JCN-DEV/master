'use strict';

angular.module('stepApp')
    .controller('HrEmpOtherServiceInfoController',
    ['$rootScope', '$scope', '$state', 'HrEmpOtherServiceInfo', 'HrEmpOtherServiceInfoSearch', 'ParseLinks',
    function ($rootScope, $scope, $state, HrEmpOtherServiceInfo, HrEmpOtherServiceInfoSearch, ParseLinks) {

        $scope.hrEmpOtherServiceInfos = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 0;
        $scope.stateName = "hrEmpOtherServiceInfo";
        $scope.loadAll = function()
        {
            if($rootScope.currentStateName == $scope.stateName){
                $scope.page = $rootScope.pageNumber;
            }
            else {
                $rootScope.pageNumber = $scope.page;
                $rootScope.currentStateName = $scope.stateName;
            }
            HrEmpOtherServiceInfo.query({page: $scope.page, size: 5000, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.hrEmpOtherServiceInfos = result;
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
            HrEmpOtherServiceInfoSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.hrEmpOtherServiceInfos = result;
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
            $scope.hrEmpOtherServiceInfo = {
                companyName: null,
                address: null,
                serviceType: null,
                position: null,
                fromDate: null,
                toDate: null,
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
    }]);
