'use strict';

angular.module('stepApp')
    .controller('PrlEmpGenSalDetailInfoController', function ($scope, $state, PrlEmpGenSalDetailInfo, PrlEmpGenSalDetailInfoSearch, ParseLinks) {

        $scope.prlEmpGenSalDetailInfos = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            PrlEmpGenSalDetailInfo.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.prlEmpGenSalDetailInfos = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            PrlEmpGenSalDetailInfoSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.prlEmpGenSalDetailInfos = result;
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
            $scope.prlEmpGenSalDetailInfo = {
                allowDeducType: null,
                allowDeducId: null,
                allowDeducName: null,
                allowDeducValue: null,
                createDate: null,
                createBy: null,
                updateDate: null,
                updateBy: null,
                id: null
            };
        };
    });
