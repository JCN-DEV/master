'use strict';

angular.module('stepApp')
    .controller('smsHomeController',
    ['$scope', '$state', 'DataUtils', 'SmsServiceComplaintsByEmployee', 'ParseLinks', 'SmsServiceDepartment', 'SmsServiceComplaint',
    function($scope, $state, DataUtils, SmsServiceComplaintsByEmployee, ParseLinks, SmsServiceDepartment, SmsServiceComplaint)
    {
        $scope.smsServiceComplaints = SmsServiceComplaintsByEmployee.query();
        //$scope.smsServiceComplaints = SmsServiceComplaint.query();
        $scope.smsservicedepartments = SmsServiceDepartment.query();
        $scope.reverse = true;
        $scope.page = 1;

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
