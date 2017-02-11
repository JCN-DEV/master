'use strict';

angular.module('stepApp')
    .controller('SmsServiceTypeController',
    ['$scope', '$state', 'SmsServiceType', 'SmsServiceTypeSearch', 'ParseLinks',
    function ($scope, $state, SmsServiceType, SmsServiceTypeSearch, ParseLinks) {

        $scope.smsServiceTypes = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            SmsServiceType.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.smsServiceTypes = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            SmsServiceTypeSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.smsServiceTypes = result;
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
            $scope.smsServiceType = {
                serviceTypeName: null,
                description: null,
                activeStatus: null,
                id: null
            };
        };
    }]);
