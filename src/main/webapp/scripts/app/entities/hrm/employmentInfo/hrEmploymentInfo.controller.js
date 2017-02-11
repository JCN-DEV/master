'use strict';

angular.module('stepApp')
    .controller('HrEmploymentInfoController',
     ['$rootScope','$scope', '$state', 'HrEmploymentInfo', 'HrEmploymentInfoSearch', 'ParseLinks',
     function ($rootScope, $scope, $state, HrEmploymentInfo, HrEmploymentInfoSearch, ParseLinks) {

        $scope.hrEmploymentInfos = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 0;
        $scope.stateName = "hrEmploymentInfo";
        $scope.loadAll = function()
        {
            if($rootScope.currentStateName == $scope.stateName){
                $scope.page = $rootScope.pageNumber;
            }
            else {
                $rootScope.pageNumber = $scope.page;
                $rootScope.currentStateName = $scope.stateName;
            }
            HrEmploymentInfo.query({page: $scope.page, size: 5000, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.hrEmploymentInfos = result;
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
            HrEmploymentInfoSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.hrEmploymentInfos = result;
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
            $scope.hrEmploymentInfo = {
                presentInstitute: null,
                joiningDate: null,
                regularizationDate: null,
                jobConfNoticeNo: null,
                confirmationDate: null,
                officeOrderNo: null,
                officeOrderDate: null,
                prlDate: null,
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
