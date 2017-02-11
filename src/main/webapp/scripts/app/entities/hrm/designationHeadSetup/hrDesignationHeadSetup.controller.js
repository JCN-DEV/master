'use strict';

angular.module('stepApp')
    .controller('HrDesignationHeadSetupController', function ($scope, $state, HrDesignationHeadSetup, HrDesignationHeadSetupSearch, ParseLinks) {

        $scope.hrDesignationHeadSetups = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            HrDesignationHeadSetup.query({page: $scope.page - 1, size: 500, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.hrDesignationHeadSetups = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            HrDesignationHeadSetupSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.hrDesignationHeadSetups = result;
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
            $scope.hrDesignationHeadSetup = {
                designationCode: null,
                designationName: null,
                designationDetail: null,
                activeStatus: true,
                createDate: null,
                createBy: null,
                updateDate: null,
                updateBy: null,
                id: null
            };
        };
    });
