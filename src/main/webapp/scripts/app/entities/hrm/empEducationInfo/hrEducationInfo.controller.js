'use strict';

angular.module('stepApp')
    .controller('HrEducationInfoController',
     ['$rootScope','$scope', '$state', 'DataUtils', 'HrEducationInfo', 'HrEducationInfoSearch', 'ParseLinks',
     function ($rootScope,$scope, $state, DataUtils, HrEducationInfo, HrEducationInfoSearch, ParseLinks) {

        $scope.hrEducationInfos = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 0;
        $scope.stateName = "hrEducationInfo";
        $scope.loadAll = function()
        {
            if($rootScope.currentStateName == $scope.stateName){
                $scope.page = $rootScope.pageNumber;
            }
            else {
                $rootScope.pageNumber = $scope.page;
                $rootScope.currentStateName = $scope.stateName;
            }
            HrEducationInfo.query({page: $scope.page, size: 5000, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.hrEducationInfos = result;
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
            HrEducationInfoSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.hrEducationInfos = result;
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
            $scope.hrEducationInfo = {
                examTitle: null,
                majorSubject: null,
                certSlNumber: null,
                sessionYear: null,
                rollNumber: null,
                instituteName: null,
                gpaOrCgpa: null,
                gpaScale: null,
                passingYear: null,
                certificateDoc: null,
                certificateDocContentType: null,
                certificateDocName: null,
                transcriptDoc: null,
                transcriptDocContentType: null,
                transcriptDocName: null,
                activeStatus: true,
                logId:null,
                logStatus:null,
                logComments:null,
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
