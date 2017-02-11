'use strict';

angular.module('stepApp')
    .controller('HrPayScaleSetupController',
     ['$scope', '$state', 'HrPayScaleSetup', 'HrPayScaleSetupSearch', 'ParseLinks',
     function ($scope, $state, HrPayScaleSetup, HrPayScaleSetupSearch, ParseLinks) {

        $scope.hrPayScaleSetups = [];
        $scope.predicate = 'payScaleCode';
        $scope.reverse = true;
        $scope.page = 0;
        $scope.loadAll = function() {
            HrPayScaleSetup.query({page: $scope.page, size: 5000, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.hrPayScaleSetups = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            HrPayScaleSetupSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.hrPayScaleSetups = result;
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
            $scope.hrPayScaleSetup = {
                payScaleCode: null,
                basicPayScale: null,
                activeStatus: true,
                createDate: null,
                createBy: null,
                updateDate: null,
                updateBy: null,
                id: null
            };
        };
    }]);
