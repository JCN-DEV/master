'use strict';

angular.module('stepApp')
    .controller('VclEmployeeAssignController',
    ['$scope', '$state', 'VclEmployeeAssign', 'VclEmployeeAssignSearch', 'ParseLinks',
    function ($scope, $state, VclEmployeeAssign, VclEmployeeAssignSearch, ParseLinks) {

        $scope.vclEmployeeAssigns = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            VclEmployeeAssign.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.vclEmployeeAssigns = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            VclEmployeeAssignSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.vclEmployeeAssigns = result;
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
            $scope.vclEmployeeAssign = {
                createDate: null,
                createBy: null,
                updateDate: null,
                updateBy: null,
                id: null
            };
        };
    }]);
