'use strict';

angular.module('stepApp')
    .controller('HrEmpPublicationInfoController',
    ['$rootScope', '$scope', '$state', 'DataUtils', 'HrEmpPublicationInfo', 'HrEmpPublicationInfoSearch', 'ParseLinks',
    function ($rootScope, $scope, $state, DataUtils, HrEmpPublicationInfo, HrEmpPublicationInfoSearch, ParseLinks) {

        $scope.hrEmpPublicationInfos = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 0;
        $scope.stateName = "hrEmpPublicationInfo";
        $scope.loadAll = function()
        {
            if($rootScope.currentStateName == $scope.stateName){
                $scope.page = $rootScope.pageNumber;
            }
            else {
                $rootScope.pageNumber = $scope.page;
                $rootScope.currentStateName = $scope.stateName;
            }
            HrEmpPublicationInfo.query({page: $scope.page, size: 5000, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.hrEmpPublicationInfos = result;
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
            HrEmpPublicationInfoSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.hrEmpPublicationInfos = result;
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
            $scope.hrEmpPublicationInfo = {
                publicationTitle: null,
                publicationDate: null,
                remarks: null,
                publicationDoc: null,
                publicationDocContentType: null,
                publicationDocName: null,
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
