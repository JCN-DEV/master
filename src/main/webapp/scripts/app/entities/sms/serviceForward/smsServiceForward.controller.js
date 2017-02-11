'use strict';

angular.module('stepApp')
    .controller('SmsServiceForwardController',
     ['$scope', '$state', 'SmsServiceForward', 'SmsServiceComplaint', 'SmsServiceForwardSearch','SmsServiceComplaintForwardSearch', 'ParseLinks',
     function ($scope, $state, SmsServiceForward, SmsServiceComplaint, SmsServiceForwardSearch,SmsServiceComplaintForwardSearch, ParseLinks) {

        $scope.smsServiceForwards = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.smsservicecomplaints = SmsServiceComplaint.query();
        $scope.loadAll = function() {
            SmsServiceForward.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.smsServiceForwards = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            SmsServiceForwardSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.smsServiceForwards = result;
            }, function(response) {
                if(response.status === 404) {
                    $scope.loadAll();
                }
            });
        };

        $scope.searchByComplaint = function (data)
        {
            SmsServiceComplaintForwardSearch.query({id: data.id}, function(result) {
                $scope.smsServiceForwards = result;
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
            $scope.smsServiceForward = {
                cc: null,
                serviceStatus: null,
                comments: null,
                forwardDate: null,
                activeStatus: null,
                id: null
            };
        };
    }]);
