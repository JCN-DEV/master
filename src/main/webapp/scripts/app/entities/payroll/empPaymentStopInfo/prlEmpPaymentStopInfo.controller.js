'use strict';

angular.module('stepApp')
    .controller('PrlEmpPaymentStopInfoController', function ($scope, $state, PrlEmpPaymentStopInfo, PrlEmpPaymentStopInfoSearch, ParseLinks) {

        $scope.prlEmpPaymentStopInfos = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            PrlEmpPaymentStopInfo.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.prlEmpPaymentStopInfos = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            PrlEmpPaymentStopInfoSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.prlEmpPaymentStopInfos = result;
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
            $scope.prlEmpPaymentStopInfo = {
                effectedDateFrom: null,
                effectedDateTo: null,
                stopActionType: null,
                activeStatus: true,
                comments: null,
                createDate: null,
                createBy: null,
                updateDate: null,
                updateBy: null,
                id: null
            };
        };
    });
