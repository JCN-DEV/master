'use strict';

angular.module('stepApp')
    .controller('HrEmpPromotionInfoController',
    ['$rootScope', '$scope', '$state', 'DataUtils', 'HrEmpPromotionInfo', 'HrEmpPromotionInfoSearch', 'ParseLinks',
    function ($rootScope, $scope, $state, DataUtils, HrEmpPromotionInfo, HrEmpPromotionInfoSearch, ParseLinks) {

        $scope.hrEmpPromotionInfos = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 0;
        $scope.stateName = "hrEmpPromotionInfo";
        $scope.loadAll = function()
        {
            if($rootScope.currentStateName == $scope.stateName){
                $scope.page = $rootScope.pageNumber;
            }
            else {
                $rootScope.pageNumber = $scope.page;
                $rootScope.currentStateName = $scope.stateName;
            }
            HrEmpPromotionInfo.query({page: $scope.page, size: 5000, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.hrEmpPromotionInfos = result;
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
            HrEmpPromotionInfoSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.hrEmpPromotionInfos = result;
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
            $scope.hrEmpPromotionInfo = {
                instituteFrom: null,
                instituteTo: null,
                departmentFrom: null,
                departmentTo: null,
                designationFrom: null,
                designationTo: null,
                payscaleFrom: null,
                payscaleTo: null,
                joiningDate: null,
                promotionType: null,
                officeOrderNo: null,
                orderDate: null,
                goDate: null,
                goDoc: null,
                goDocContentType: null,
                goDocName: null,
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
