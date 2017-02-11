'use strict';

angular.module('stepApp')
    .controller('PrlSalaryGenerationLogController', function ($scope, $state, PrlSalaryGenerationLog, PrlSalaryGenerationLogSearch, ParseLinks) {

        $scope.prlSalaryGenerationLogs = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            PrlSalaryGenerationLog.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.prlSalaryGenerationLogs = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            PrlSalaryGenerationLogSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.prlSalaryGenerationLogs = result;
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
            $scope.prlSalaryGenerationLog = {
                generateDate: null,
                comments: null,
                actionStatus: null,
                generateBy: null,
                createDate: null,
                createBy: null,
                id: null
            };
        };
    });
