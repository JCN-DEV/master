'use strict';

angular.module('stepApp')
    .controller('VclVehicleDriverAssignController',
    ['$scope', '$state', 'VclVehicleDriverAssign', 'VclVehicleDriverAssignSearch', 'ParseLinks',
    function ($scope, $state, VclVehicleDriverAssign, VclVehicleDriverAssignSearch, ParseLinks) {

        $scope.vclVehicleDriverAssigns = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            VclVehicleDriverAssign.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.vclVehicleDriverAssigns = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            VclVehicleDriverAssignSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.vclVehicleDriverAssigns = result;
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
            $scope.vclVehicleDriverAssign = {
                createDate: null,
                createBy: null,
                updateDate: null,
                updateBy: null,
                id: null
            };
        };
    }]);
