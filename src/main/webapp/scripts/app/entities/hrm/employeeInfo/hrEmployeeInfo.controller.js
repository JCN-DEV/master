'use strict';

angular.module('stepApp')
    .controller('HrEmployeeInfoController',
    ['$rootScope', '$scope', '$state', 'HrEmployeeInfo', 'HrEmployeeInfoSearch', 'ParseLinks',
        function ($rootScope, $scope, $state, HrEmployeeInfo, HrEmployeeInfoSearch, ParseLinks) {

            $scope.hrEmployeeInfos = [];
            $scope.predicate = 'id';
            $scope.reverse = true;
            $scope.page = 0;
            $scope.stateName = "hrEmployeeInfo";
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

                HrEmployeeInfo.query({page: $scope.page, size: 5000, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                    $scope.links = ParseLinks.parse(headers('link'));
                    $scope.totalItems = headers('X-Total-Count');
                    $scope.hrEmployeeInfos = result;
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
                HrEmployeeInfoSearch.query({query: $scope.searchQuery}, function(result) {
                    $scope.hrEmployeeInfos = result;
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
                $scope.hrEmployeeInfo = {
                    fullName: null,
                    fullNameBn: null,
                    fatherName: null,
                    fatherNameBn: null,
                    motherName: null,
                    motherNameBn: null,
                    birthDate: null,
                    apointmentGoDate: null,
                    presentId: null,
                    nationalId: null,
                    emailAddress: null,
                    mobileNumber: null,
                    gender: null,
                    birthPlace: null,
                    anyDisease: null,
                    officerStuff: null,
                    tinNumber: null,
                    maritalStatus: null,
                    bloodGroup: null,
                    nationality: null,
                    quota: null,
                    birthCertificateNo: null,
                    religion: null,
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
