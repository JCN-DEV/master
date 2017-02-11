'use strict';

angular.module('stepApp')
    .controller('HrDepartmentHeadSetupController', function ($scope, $state, HrDepartmentHeadSetup, HrDepartmentHeadSetupSearch, ParseLinks) {

        $scope.hrDepartmentHeadSetups = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.currentPage = 1;
        $scope.pageSize = 10;
        $scope.loadAll = function() {
            HrDepartmentHeadSetup.query({page: $scope.page - 1, size: 500, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.hrDepartmentHeadSetups = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            HrDepartmentHeadSetupSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.hrDepartmentHeadSetups = result;
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
            $scope.hrDepartmentHeadSetup = {
                departmentCode: null,
                departmentName: null,
                departmentDetail: null,
                activeStatus: true,
                createDate: null,
                createBy: null,
                updateDate: null,
                updateBy: null,
                id: null
            };
        };
    });
