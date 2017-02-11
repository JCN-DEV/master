'use strict';

angular.module('stepApp')
    .controller('HrEmpAuditObjectionInfoController',
    ['$rootScope','$scope', '$state', 'HrEmpAuditObjectionInfo', 'HrEmpAuditObjectionInfoSearch', 'ParseLinks',
    function ($rootScope,$scope, $state, HrEmpAuditObjectionInfo, HrEmpAuditObjectionInfoSearch, ParseLinks) {

        $scope.hrEmpAuditObjectionInfos = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 0;
        $scope.stateName = "hrEmpAuditObjectionInfo";
        $scope.loadAll = function()
        {
            if($rootScope.currentStateName == $scope.stateName){
                $scope.page = $rootScope.pageNumber;
            }
            else {
                $rootScope.pageNumber = $scope.page;
                $rootScope.currentStateName = $scope.stateName;
            }
            HrEmpAuditObjectionInfo.query({page: $scope.page, size: 5000, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.hrEmpAuditObjectionInfos = result;
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
            HrEmpAuditObjectionInfoSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.hrEmpAuditObjectionInfos = result;
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
            $scope.hrEmpAuditObjectionInfo = {
                organizationName: null,
                auditYear: null,
                paragraphNumber: null,
                objectionHeadliine: null,
                objectionAmount: null,
                officeReplyNumber: null,
                replyDate: null,
                jointMeetingNumber: null,
                jointMeetingDate: null,
                isSettled: null,
                remarks: null,
                logId: null,
                logStatus: null,
                activeStatus: true,
                createDate: null,
                createBy: null,
                updateDate: null,
                updateBy: null,
                id: null
            };
        };
    }]);
