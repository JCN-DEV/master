'use strict';

angular.module('stepApp')
    .controller('HrEmplTypeInfoController',
    ['$scope','$state', 'HrEmplTypeInfo', 'HrEmplTypeInfoSearch', 'ParseLinks',
    function ($scope,$state, HrEmplTypeInfo, HrEmplTypeInfoSearch, ParseLinks) {

        $scope.hrEmplTypeInfos = [];
        $scope.predicate = 'typeName';
        $scope.reverse = true;
        $scope.page = 0;
        $scope.currentPage = 1;
        $scope.pageSize = 10;
        $scope.loadAll = function() {
            HrEmplTypeInfo.query({page: $scope.page, size: 5000, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.hrEmplTypeInfos = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();





        $scope.search = function () {
            HrEmplTypeInfoSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.hrEmplTypeInfos = result;
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
            $scope.hrEmplTypeInfo = {
                typeCode: null,
                typeName: null,
                typeDetail: null,
                activeStatus: true,
                createDate: null,
                createBy: null,
                updateDate: null,
                updateBy: null,
                id: null
            };
        };
    }]);
