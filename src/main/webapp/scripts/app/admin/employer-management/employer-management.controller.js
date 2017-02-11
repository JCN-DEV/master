'use strict';

angular.module('stepApp')
    .controller('EmployerManagementController',
     ['$scope', 'PendingTempEmployerInstitute', 'RejectedTempEmployerInstitute','User','ApprovedTempEmployerInstitute', 'TempEmployerSearch', 'ParseLinks', 'Language', 'EmployerByUserId', 'TempEmployer',
          function ($scope, PendingTempEmployerInstitute, RejectedTempEmployerInstitute,User,ApprovedTempEmployerInstitute, TempEmployerSearch, ParseLinks, Language, EmployerByUserId, TempEmployer) {

        $scope.singleView = false;
        $scope.pendingEmployers = [];
        $scope.approvedEmployers = [];
        $scope.rejectedEmployers = [];
        $scope.authorities = ["ROLE_ADMIN","ROLE_JPADMIN"];
        $scope.showOldInfo = true;

        $scope.tempEmployer = {};
        $scope.employer = {};

        $scope.pagePending = 0;
        $scope.pageApproved = 0;
        $scope.pageRejected = 0;
        $scope.perPage = 5000000;

        $scope.pendingEmployer = function () {
            $scope.showOldInfo = true;
            PendingTempEmployerInstitute.query({page: $scope.pagePending, size: $scope.perPage}, function (result, headers) {
                $scope.linksPending = ParseLinks.parse(headers('link'));
                $scope.pendingEmployers = result;
                $scope.totalPending = headers('x-total-count');
            });
            $scope.hideSingleView();
        };

        $scope.loadPagePendingEmployer = function(pagePending) {

            $scope.pagePending = pagePending;
            $scope.pendingEmployer();
        };

        $scope.approvedEmployer = function () {
            $scope.showOldInfo = false;
            ApprovedTempEmployerInstitute.query({page: $scope.pageApproved, size: $scope.perPage}, function (result, headers) {
                $scope.linksApproved = ParseLinks.parse(headers('link'));
                $scope.approvedEmployers = result;
                $scope.totalApproved = headers('x-total-count');
            });
            $scope.hideSingleView();
        };

        $scope.loadPageApprovedEmployer = function(pageApproved) {

            $scope.pageApproved = pageApproved;
            $scope.approvedEmployer();
        };

        $scope.rejectedEmployer = function () {
            RejectedTempEmployerInstitute.query({page: $scope.pageRejected, size: $scope.perPage}, function (result, headers) {
                $scope.linksRejected = ParseLinks.parse(headers('link'));
                $scope.rejectedEmployers = result;
                $scope.totalRejected = headers('x-total-count');
            });
            $scope.hideSingleView();
        };

        $scope.loadPageRejectedEmployer = function(pageRejected) {
            $scope.showOldInfo = false;
            $scope.pageRejected = pageRejected;
            $scope.rejectedEmployer();
        };

        $scope.employerDetail = function (employerId) {

            TempEmployer.get({id: employerId}, function(tempEmployer){
                $scope.tempEmployer = tempEmployer;

                EmployerByUserId.get({id: $scope.tempEmployer.user.id}, function (employer) {
                        $scope.employer = employer;
                    },
                    function (response) {
                        if (response.status === 404) {
                            $scope.employer = {};
                        }
                    }
                );
            });

            $scope.showSingleView();
        };

        $scope.showSingleView = function()
        {
            $scope.singleView = true;
        };

        $scope.hideSingleView = function()
        {
            $scope.singleView = false;
        };

        $scope.setActive = function (user, isActivated) {
            user.activated = isActivated;
            User.update(user, function () {
                $scope.loadAll();
                $scope.clear();
            });
        };

        $scope.order = function (predicate, reverse) {
            $scope.predicate = predicate;
            $scope.reverse = reverse;
            User.query({page: $scope.page, per_page: 20}, function (result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.users = result;
                $scope.total = headers('x-total-count');
            });
        };

        $scope.search = function () {
            TempEmployerSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.pendingEmployers = result;
            }, function(response) {
                if(response.status === 404) {
                    $scope.loadAll();
                }
            });
        };

        $scope.sync = function (){
            console.log('dfasdf');
            TempEmployer.query(function(results){
                alert('>>>>>>>>>>>>>>>.');
                console.log(results);
            });
            //console.log(all);
            //TempEmployer.update(results);
        };

        $scope.areAllTempEmployersSelected = false;

        $scope.updateTempEmployersSelection = function (TempEmployerArray, selectionValue) {
            for (var i = 0; i < TempEmployerArray.length; i++)
            {
                TempEmployerArray[i].isSelected = selectionValue;
            }
        };

    }]);
