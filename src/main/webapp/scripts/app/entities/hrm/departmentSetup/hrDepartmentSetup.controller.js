'use strict';

angular.module('stepApp')
    .controller('HrDepartmentSetupController',
    ['$rootScope','$scope', '$state', 'HrDepartmentSetup', 'HrDepartmentSetupSearch', 'ParseLinks',
        function ($rootScope, $scope, $state, HrDepartmentSetup, HrDepartmentSetupSearch, ParseLinks) {

            $scope.hrDepartmentSetups = [];
            $scope.predicate = 'id';
            $scope.reverse = true;
            $scope.page = 0;
            $scope.stateName = "hrDepartmentSetup";
            $scope.loadAll = function()
            {
                if($rootScope.currentStateName == $scope.stateName){
                    $scope.page = $rootScope.pageNumber;
                }
                else {
                    $rootScope.pageNumber = $scope.page;
                    $rootScope.currentStateName = $scope.stateName;
                }
                HrDepartmentSetup.query({page: $scope.page, size: 500, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                    $scope.links = ParseLinks.parse(headers('link'));
                    $scope.totalItems = headers('X-Total-Count');
                    $scope.hrDepartmentSetups = result;
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
                HrDepartmentSetupSearch.query({query: $scope.searchQuery}, function(result) {
                    $scope.hrDepartmentSetups = result;
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
                $scope.hrDepartmentSetup = {
                    activeStatus: true,
                    createDate: null,
                    createBy: null,
                    updateDate: null,
                    updateBy: null,
                    id: null
                };
            };
        }]);
