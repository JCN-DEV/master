'use strict';

angular.module('stepApp')
    .controller('SmsServiceComplaintController',
    ['$scope', '$state', 'DataUtils', 'SmsServiceComplaint', 'SmsServiceComplaintSearch', 'ParseLinks', 'SmsServiceDepartment', 'SmsServiceComplaintDepartmentSearch',
    function ($scope, $state, DataUtils, SmsServiceComplaint, SmsServiceComplaintSearch, ParseLinks, SmsServiceDepartment, SmsServiceComplaintDepartmentSearch) {

        $scope.smsServiceComplaints = [];
        $scope.predicate = 'id';
        $scope.smsservicedepartments = SmsServiceDepartment.query();
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            SmsServiceComplaint.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.smsServiceComplaints = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.searchByDeparment = function (data)
        {
            if(data != null)
            {
                SmsServiceComplaintDepartmentSearch.query({id: data.id}, function (result) {
                    $scope.smsServiceComplaints = result;
                }, function (response) {
                    if (response.status === 404) {
                        $scope.loadAll();
                    }
                });
            }
            else{
                $scope.loadAll();
            }
        };

        $scope.search = function () {
            SmsServiceComplaintSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.smsServiceComplaints = result;
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
            $scope.smsServiceComplaint = {
                previousCode: null,
                priority: null,
                complaintName: null,
                fullName: null,
                emailAddress: null,
                contactNumber: null,
                description: null,
                complaintDoc: null,
                complaintDocContentType: null,
                activeStatus: null,
                id: null
            };
        };

        $scope.abbreviate = DataUtils.abbreviate;

        $scope.byteSize = DataUtils.byteSize;
    }]);
