'use strict';

angular.module('stepApp')
    .controller('HrDesignationSetupController',
    ['$rootScope','$scope', '$state', 'HrDesignationSetup', 'HrDesignationSetupSearch', 'ParseLinks',
        function ($rootScope, $scope, $state, HrDesignationSetup, HrDesignationSetupSearch, ParseLinks) {

            $scope.hrDesignationSetups = [];
            $scope.predicate = 'id';
            $scope.reverse = true;
            $scope.stateName = "hrDesignationSetup";
            $scope.page = 0;
            $scope.loadAll = function()
            {
                if($rootScope.currentStateName == $scope.stateName){
                    $scope.page = $rootScope.pageNumber;
                }
                else {
                    $rootScope.pageNumber = $scope.page;
                    $rootScope.currentStateName = $scope.stateName;
                }
                HrDesignationSetup.query({page: $scope.page, size: 500, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                    $scope.links = ParseLinks.parse(headers('link'));
                    $scope.totalItems = headers('X-Total-Count');
                    $scope.hrDesignationSetups = result;
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
                HrDesignationSetupSearch.query({query: $scope.searchQuery}, function(result) {
                    $scope.hrDesignationSetups = result;
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
                $scope.hrDesignationSetup = {
                    elocattedPosition: null,
                    activeStatus: true,
                    createDate: null,
                    createBy: null,
                    updateDate: null,
                    updateBy: null,
                    id: null
                };
            };
        }]);
