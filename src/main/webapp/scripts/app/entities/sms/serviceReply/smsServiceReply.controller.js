'use strict';

angular.module('stepApp')
    .controller('SmsServiceReplyController',
    ['$scope', '$state', 'SmsServiceReply', 'SmsServiceReplySearch', 'ParseLinks', 'SmsServiceComplaint', 'SmsServiceComplaintReplySearch', 'SmsServiceDepartment', 'SmsServiceReplySearchByDepartment',
    function ($scope, $state, SmsServiceReply, SmsServiceReplySearch, ParseLinks, SmsServiceComplaint, SmsServiceComplaintReplySearch, SmsServiceDepartment, SmsServiceReplySearchByDepartment) {

        $scope.smsServiceReplys = [];
        $scope.smsservicedepartments = SmsServiceDepartment.query();
        $scope.smsservicecomplaints = SmsServiceComplaint.query();
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            SmsServiceReply.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.smsServiceReplys = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.search = function () {
            SmsServiceReplySearch.query({query: $scope.searchQuery}, function(result) {
                $scope.smsServiceReplys = result;
            }, function(response) {
                if(response.status === 404) {
                    $scope.loadAll();
                }
            });
        };

        $scope.searchByComplaint = function (data)
        {
            SmsServiceComplaintReplySearch.query({id: data.id}, function(result) {
                $scope.smsServiceReplys = result;
            }, function(response) {
                if(response.status === 404) {
                    $scope.loadAll();
                }
            });
        };

        $scope.searchByDepartment = function (data)
        {
            console.log("deptId: "+data.smsServiceDepartment.id);
            SmsServiceReplySearchByDepartment.query({id: data.id}, function(result) {
                $scope.smsServiceReplys = result;
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
            $scope.smsServiceReply = {
                cc: null,
                comments: null,
                replyDate: null,
                id: null
            };
        };
    }]);
