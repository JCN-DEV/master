'use strict';

angular.module('stepApp')
    .controller('PgmsNotificationSentListRequestController',
        ['$scope', 'PgmsNotification', 'PgmsNotificationSearch', 'ParseLinks',
        function ($scope, PgmsNotification, PgmsNotificationSearch, ParseLinks) {

        $scope.pgmsNotifications = [];
        $scope.page = 0;

        $scope.loadAll = function() {
           PgmsNotification.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.pgmsNotifications = result;
                console.log(" Notification Send List: "+JSON.stringify($scope.pgmsNotifications));
           });
        };

        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.search = function () {
            PgmsNotificationSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.pgmsNotifications = result;
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

        $scope.approvalViewDetail = function (dataObj)
        {
            $scope.approvalObj = dataObj;
            $('#approveViewDetailForm').modal('show');
        };

        $scope.clear = function () {
            $scope.PgmsNotification = {
                empId: null,
                empName: null,
                empDesignation: null,
                dateOfBirth: null,
                joiningDate: null,
                retiremnntDate: null,
                workDuration: null,
                contactNumber: null,
                message: null,
                notificationStatus: null,
                activeStatus: null,
                createDate: null,
                createBy: null,
                updateDate: null,
                updateBy: null,
                id: null
            };
        };

    }]);
