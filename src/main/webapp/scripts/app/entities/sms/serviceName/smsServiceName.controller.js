'use strict';

angular.module('stepApp')
    .controller('SmsServiceNameController',
     ['$scope', '$state', 'SmsServiceName', 'SmsServiceNameSearch', 'ParseLinks',
     function ($scope, $state, SmsServiceName, SmsServiceNameSearch, ParseLinks) {

        $scope.smsServiceNames = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 0;
        $scope.loadAll = function() {
            SmsServiceName.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.smsServiceNames = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            SmsServiceNameSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.smsServiceNames = result;
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
            $scope.smsServiceName = {
                serviceName: null,
                description: null,
                activeStatus: null,
                id: null
            };
        };
    }]);
