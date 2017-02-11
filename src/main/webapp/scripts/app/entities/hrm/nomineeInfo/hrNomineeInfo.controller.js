'use strict';

angular.module('stepApp')
    .controller('HrNomineeInfoController',
    ['$rootScope', '$scope', '$state', 'HrNomineeInfo', 'HrNomineeInfoSearch', 'ParseLinks',
    function ($rootScope, $scope, $state, HrNomineeInfo, HrNomineeInfoSearch, ParseLinks) {

        $scope.hrNomineeInfos = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 0;
        $scope.stateName = "hrNomineeInfo";
        $scope.loadAll = function()
        {
            if($rootScope.currentStateName == $scope.stateName){
                $scope.page = $rootScope.pageNumber;
            }
            else {
                $rootScope.pageNumber = $scope.page;
                $rootScope.currentStateName = $scope.stateName;
            }
            HrNomineeInfo.query({page: $scope.page, size: 5000, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.hrNomineeInfos = result;
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
            HrNomineeInfoSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.hrNomineeInfos = result;
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
            $scope.hrNomineeInfo = {
                nomineeName: null,
                nomineeNameBn: null,
                birthDate: null,
                gender: null,
                relationship: null,
                occupation: null,
                designation: null,
                nationalId: null,
                mobileNumber: null,
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
