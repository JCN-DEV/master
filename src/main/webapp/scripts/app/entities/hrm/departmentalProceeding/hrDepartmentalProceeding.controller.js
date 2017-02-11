'use strict';

angular.module('stepApp')
    .controller('HrDepartmentalProceedingController',
    ['$rootScope','$scope', '$state','DataUtils', 'HrDepartmentalProceeding', 'HrDepartmentalProceedingSearch', 'ParseLinks',
    function ($rootScope,$scope, $state, DataUtils, HrDepartmentalProceeding, HrDepartmentalProceedingSearch, ParseLinks) {

        $scope.hrDepartmentalProceedings = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 0;
        $scope.stateName = "hrDepartmentalProceeding";
        $scope.loadAll = function()
        {
            if($rootScope.currentStateName == $scope.stateName){
                $scope.page = $rootScope.pageNumber;
            }
            else {
                $rootScope.pageNumber = $scope.page;
                $rootScope.currentStateName = $scope.stateName;
            }
            HrDepartmentalProceeding.query({page: $scope.page, size: 5000, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.hrDepartmentalProceedings = result;
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
            HrDepartmentalProceedingSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.hrDepartmentalProceedings = result;
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
            $scope.hrDepartmentalProceeding = {
                crimeNature: null,
                nature: null,
                amount: null,
                formDate: null,
                toDate: null,
                period: null,
                dudakCaseDetail: null,
                dudakPunishment: null,
                goDate: null,
                goDoc: null,
                goDocContentType: null,
                goDocName: null,
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

        $scope.abbreviate = DataUtils.abbreviate;

        $scope.byteSize = DataUtils.byteSize;
    }]);
