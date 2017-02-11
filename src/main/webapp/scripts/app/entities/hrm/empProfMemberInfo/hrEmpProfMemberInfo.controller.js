'use strict';

angular.module('stepApp')
    .controller('HrEmpProfMemberInfoController',
    ['$rootScope', '$scope', '$state', 'HrEmpProfMemberInfo', 'HrEmpProfMemberInfoSearch', 'ParseLinks',
    function ($rootScope, $scope, $state, HrEmpProfMemberInfo, HrEmpProfMemberInfoSearch, ParseLinks) {

        $scope.hrEmpProfMemberInfos = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 0;
        $scope.stateName = "hrEmpProfMemberInfo";
        $scope.loadAll = function()
        {
            if($rootScope.currentStateName == $scope.stateName){
                $scope.page = $rootScope.pageNumber;
            }
            else {
                $rootScope.pageNumber = $scope.page;
                $rootScope.currentStateName = $scope.stateName;
            }
            HrEmpProfMemberInfo.query({page: $scope.page, size: 5000, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.hrEmpProfMemberInfos = result;
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
            HrEmpProfMemberInfoSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.hrEmpProfMemberInfos = result;
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
            $scope.hrEmpProfMemberInfo = {
                organizationName: null,
                membershipNumber: null,
                membershipDate: null,
                logId: null,
                logStatus: null,
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
