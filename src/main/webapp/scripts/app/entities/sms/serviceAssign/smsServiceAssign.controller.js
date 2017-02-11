'use strict';

angular.module('stepApp')
    .controller('SmsServiceAssignController',
    ['$scope', '$state', 'SmsServiceAssign', 'SmsServiceAssignSearch', 'ParseLinks',
    function ($scope, $state, SmsServiceAssign, SmsServiceAssignSearch, ParseLinks) {

        $scope.smsServiceAssigns = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            SmsServiceAssign.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.smsServiceAssigns = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            SmsServiceAssignSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.smsServiceAssigns = result;
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
            $scope.smsServiceAssign = {
                activeStatus: null,
                id: null
            };
        };
    }]);
