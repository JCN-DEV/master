'use strict';

angular.module('stepApp')
    .controller('HrClassInfoController',
    ['$scope', '$state', 'HrClassInfo', 'HrClassInfoSearch', 'ParseLinks',
    function ($scope, $state, HrClassInfo, HrClassInfoSearch, ParseLinks) {

        $scope.hrClassInfos = [];
        $scope.predicate = 'className';
        $scope.reverse = true;
        $scope.page = 0;
        $scope.loadAll = function() {
            HrClassInfo.query({page: $scope.page, size: 5000, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.hrClassInfos = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.search = function () {
            HrClassInfoSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.hrClassInfos = result;
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
            $scope.hrClassInfo = {
                classCode: null,
                className: null,
                classDetail: null,
                activeStatus: false,
                createDate: null,
                createBy: null,
                updateDate: null,
                updateBy: null,
                id: null
            };
        };
    }]);
