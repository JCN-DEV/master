'use strict';

angular.module('stepApp')
    .controller('HrSpouseInfoController',
    ['$rootScope', '$scope', '$state', 'HrSpouseInfo', 'HrSpouseInfoSearch', 'ParseLinks',
    function ($rootScope, $scope, $state, HrSpouseInfo, HrSpouseInfoSearch, ParseLinks) {

        $scope.hrSpouseInfos = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 0;
        $scope.stateName = "hrSpouseInfo";
        $scope.loadAll = function()
        {
            if($rootScope.currentStateName == $scope.stateName){
                $scope.page = $rootScope.pageNumber;
            }
            else {
                $rootScope.pageNumber = $scope.page;
                $rootScope.currentStateName = $scope.stateName;
            }

            //console.log("pg: "+$scope.page+", pred:"+$scope.predicate+", rootPage: "+$rootScope.pageNumber+", rootSc: "+$rootScope.currentStateName+", lcst:"+$scope.stateName);
            HrSpouseInfo.query({page: $scope.page, size: 5000, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.hrSpouseInfos = result;
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
            HrSpouseInfoSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.hrSpouseInfos = result;
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
            $scope.hrSpouseInfo = {
                spouseName: null,
                spouseNameBn: null,
                birthDate: null,
                gender: null,
                relationship: null,
                isNominee: null,
                occupation: null,
                organization: null,
                designation: null,
                nationalId: null,
                emailAddress: null,
                contactNumber: null,
                address: null,
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
