'use strict';

angular.module('stepApp')
    .controller('HrEntertainmentBenefitController',
     ['$rootScope', '$scope', '$state', 'HrEntertainmentBenefit', 'HrEntertainmentBenefitSearch','ParseLinks',
     function ($rootScope, $scope, $state, HrEntertainmentBenefit, HrEntertainmentBenefitSearch, ParseLinks) {

        $scope.hrEntertainmentBenefits = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 0;
        $scope.stateName = "hrEntertainmentBenefit";
        $scope.loadAll = function()
        {
            if($rootScope.currentStateName == $scope.stateName){
                $scope.page = $rootScope.pageNumber;
            }
            else {
                $rootScope.pageNumber = $scope.page;
                $rootScope.currentStateName = $scope.stateName;
            }
            HrEntertainmentBenefit.query({page: $scope.page, size: 5000, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.hrEntertainmentBenefits = result;
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
            HrEntertainmentBenefitSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.hrEntertainmentBenefits = result;
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
            $scope.hrEntertainmentBenefit = {
                eligibilityDate: null,
                amount: null,
                totalDays: null,
                notTakenReason: null,
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
